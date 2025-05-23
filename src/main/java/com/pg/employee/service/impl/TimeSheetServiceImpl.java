package com.pg.employee.service.impl;

import com.pg.employee.dto.request.timeSheet.CreateTimeSheetDto;
import com.pg.employee.entities.TimeSheetEntity;
import com.pg.employee.entities.WorkScheduleEntity;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import com.pg.employee.repository.TimeSheetRepository;
import com.pg.employee.repository.WorkScheduleRepository;
import com.pg.employee.service.TimeSheetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class TimeSheetServiceImpl implements TimeSheetService {

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Override
    public TimeSheetEntity createTimeSheet(CreateTimeSheetDto createTimeSheetDto, Account account) {
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
                return timeSheetEntity;
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
                return timeSheetEntity;
            }
        } catch (Exception e) {
            log.error("Error create work schedule: ", e);
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
