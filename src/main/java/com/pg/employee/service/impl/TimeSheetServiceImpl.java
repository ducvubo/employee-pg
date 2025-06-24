package com.pg.employee.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.employee.dto.request.timeSheet.CreateTimeSheetDto;
import com.pg.employee.entities.TimeSheetEntity;
import com.pg.employee.entities.WorkScheduleEntity;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import com.pg.employee.models.CreateNotification;
import com.pg.employee.models.CreateNotificationEmployee;
import com.pg.employee.repository.TimeSheetRepository;
import com.pg.employee.repository.WorkScheduleRepository;
import com.pg.employee.service.TimeSheetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class TimeSheetServiceImpl implements TimeSheetService {

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Override
    public WorkScheduleEntity createTimeSheet(CreateTimeSheetDto createTimeSheetDto, Account account) {
        try {
            //tìm work shcedule theo ws_date và ws_res_id
            String employeeId = createTimeSheetDto.getTsEmployeeId();
            OffsetDateTime checkInTime = OffsetDateTime.parse(createTimeSheetDto.getTsCheckIn());
            ZonedDateTime zonedCheckInTime = checkInTime.atZoneSameInstant(ZoneId.of("Asia/Ho_Chi_Minh"));

// Tính ngày bắt đầu và kết thúc để tìm ca làm
            ZonedDateTime startOfDay = zonedCheckInTime.toLocalDate().atStartOfDay(ZoneId.of("Asia/Ho_Chi_Minh"));
            ZonedDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

            Date startDateConvert = Date.from(startOfDay.withZoneSameInstant(ZoneOffset.UTC).toInstant());
            Date endDateConvert = Date.from(endOfDay.withZoneSameInstant(ZoneOffset.UTC).toInstant());

            List<WorkScheduleEntity> workScheduleEntities = workScheduleRepository.findByWsDateOnly(
                    startDateConvert,
                    endDateConvert,
                    account.getAccountRestaurantId()
            );

            if (workScheduleEntities.isEmpty()) {
                throw new BadRequestError("Không tìm thấy lịch làm việc nào cho ngày này");
            }


            Optional<WorkScheduleEntity> matchedSchedule = workScheduleEntities.stream()
                    .filter(schedule -> schedule.getListEmployeeId() != null && schedule.getListEmployeeId().contains(employeeId))
                    .findFirst();

            if (matchedSchedule.isEmpty()) {
                throw new BadRequestError("Nhân viên không có ca làm việc trong ngày này");
            }

            WorkScheduleEntity foundSchedule = matchedSchedule.get();

            if ("F".equals(foundSchedule.getWs_status())) {
                throw new BadRequestError("Nhân viên không có ca làm việc trong ngày này");
            }


            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            String rawStartTime = foundSchedule.getWorkingShift().getWks_start_time();
            String rawEndTime = foundSchedule.getWorkingShift().getWks_end_time();

            String fixedStartTime = fixTimeFormat(rawStartTime);
            String fixedEndTime = fixTimeFormat(rawEndTime);

            LocalTime startTime = LocalTime.parse(fixedStartTime, timeFormatter);
            LocalTime endTime = LocalTime.parse(fixedEndTime, timeFormatter);
            LocalTime checkTime = zonedCheckInTime.toLocalTime();

            // Tính khoảng giữa ca
            Duration halfDuration = Duration.between(startTime, endTime).dividedBy(2);
            LocalTime midTime = startTime.plus(halfDuration);

            // So sánh thời gian checkIn với midTime
            boolean isFirstHalf = checkTime.isBefore(midTime);

            // Tạo TimeSheetEntity với thời gian kiểu Date (không giữ OffsetDateTime)
            Date checkInDate = Date.from(zonedCheckInTime.toInstant());

            Optional<TimeSheetEntity> existingTimeSheet = timeSheetRepository
                    .findExisting(employeeId, foundSchedule);


            if (existingTimeSheet.isPresent()) {
                TimeSheetEntity timeSheetEntity = existingTimeSheet.get();

                if (isFirstHalf) {
                    timeSheetEntity.setTsCheckIn(checkInDate);
                } else {
                    timeSheetEntity.setTsCheckOut(checkInDate);
                }

                timeSheetEntity.setUpdatedAt(new Date());

                timeSheetRepository.save(timeSheetEntity); // update
                // Gửi thông báo cho nhân viên
                String content = isFirstHalf ? "Bạn đã chấm công vào lúc " + zonedCheckInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " trong ca làm việc " + foundSchedule.getWorkingShift().getWks_name() :
                        "Bạn đã chấm công ra lúc " + zonedCheckInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " trong ca làm việc " + foundSchedule.getWorkingShift().getWks_name();
                CreateNotificationEmployee createNotificationEmployee = CreateNotificationEmployee.builder()
                        .notiEplId(employeeId)
                        .notiTitle("Chấm công")
                        .notiContent(content)
                        .notiType("WORK_SCHEDULE")
                        .notiMetadata("no metadata")
                        .sendObject("one_account")
                        .build();
                String json = new ObjectMapper().writeValueAsString(createNotificationEmployee);
                kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);
                return foundSchedule;
            } else {
                TimeSheetEntity timeSheetEntity = TimeSheetEntity.builder()
                        .tsCheckIn(isFirstHalf ? checkInDate : null)
                        .tsCheckOut(isFirstHalf ? null : checkInDate)
                        .tsResId(account.getAccountRestaurantId())
                        .tsEmployeeId(employeeId)
                        .tsWsId(foundSchedule)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();
                timeSheetRepository.save(timeSheetEntity);
                // Gửi thông báo cho nhân viên
                String content = isFirstHalf ? "Bạn đã chấm công vào lúc " + zonedCheckInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "Trong ca làm việc " + foundSchedule.getWorkingShift().getWks_name() :
                        "Bạn đã chấm công ra lúc " + zonedCheckInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "Trong ca làm việc " + foundSchedule.getWorkingShift().getWks_name();
                CreateNotificationEmployee createNotificationEmployee = CreateNotificationEmployee.builder()
                        .notiEplId(employeeId)
                        .notiTitle("Chấm công")
                        .notiContent(content)
                        .notiType("WORK_SCHEDULE")
                        .notiMetadata("no metadata")
                        .sendObject("one_account")
                        .build();
                String json = new ObjectMapper().writeValueAsString(createNotificationEmployee);
                kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);

                return foundSchedule;
            }
        } catch (Exception e) {
            log.error("Error create work schedule: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TimeSheetEntity> getTimeSheetsByWorkScheduleId(String wsId, Account account) {
        try {
            UUID wsIdUuid = UUID.fromString(wsId);
            Optional<WorkScheduleEntity> workScheduleEntity = workScheduleRepository.findById(wsIdUuid);
            if (workScheduleEntity.isEmpty()) {
                throw new BadRequestError("Lịch làm việc không tồn tại");
            }
            return timeSheetRepository.findByTsWsIdAndTsResId(workScheduleEntity.get(), account.getAccountRestaurantId());
        } catch (Exception e) {
            log.error("Error get time sheets by work schedule id: ", e);
            throw new RuntimeException(e);
        }
    }

    private String fixTimeFormat(String time) {
        String[] parts = time.split(":");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() == 1) {
                sb.append("0").append(parts[i]); // Thêm số 0 trước nếu chỉ có 1 chữ số
            } else {
                sb.append(parts[i]);
            }
            if (i < parts.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }
}
