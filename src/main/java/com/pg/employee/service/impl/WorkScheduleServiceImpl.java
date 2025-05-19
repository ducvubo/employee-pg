package com.pg.employee.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.employee.grpc.EmployeeProto.Employee;
import com.pg.employee.grpc.api.Api;
import com.pg.employee.config.GrpcClient;
import com.pg.employee.dto.request.workSchedule.CreateWorkScheduleDto;
import com.pg.employee.dto.request.workSchedule.UpdateWorkScheduleDto;
import com.pg.employee.entities.LabelEntity;
import com.pg.employee.entities.WorkScheduleEntity;
import com.pg.employee.entities.WorkingShiftEntity;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import com.pg.employee.models.CreateNotification;
import com.pg.employee.models.EmployeeModel;
import com.pg.employee.repository.LabelRepository;
import com.pg.employee.repository.WorkScheduleRepository;
import com.pg.employee.repository.WorkingShiftRepository;
import com.pg.employee.service.WorkScheduleService;
import com.pg.employee.utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date;

@Service
@Slf4j
public class WorkScheduleServiceImpl implements WorkScheduleService {

//    private final GrpcClient grpcClient;
//
//    public WorkScheduleServiceImpl(GrpcClient grpcClient) {
//        this.grpcClient = grpcClient;
//    }

    @Autowired
    private GrpcClient grpcClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private WorkingShiftRepository workingShiftRepository;

    @Autowired
    private LabelRepository labelRepository;

    private boolean topicExists(String topicName) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            return adminClient.listTopics().names().get().contains(topicName);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tạo topic nếu chưa tồn tại
    private void createTopic(String topicName) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            if (!topicExists(topicName)) {
                NewTopic newTopic = new NewTopic(topicName, 3, (short) 1);
                adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
                System.out.println("✅ Created topic: " + topicName);
            } else {
                System.out.println("⚠️ Topic already exists: " + topicName);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Override
    public WorkScheduleEntity createWorkSchedule(CreateWorkScheduleDto createWorkScheduleDto, Account account) {
        try{
            UUID lb_id = UUID.fromString(createWorkScheduleDto.getLb_id());
            UUID wks_id = UUID.fromString(createWorkScheduleDto.getWks_id());
            Optional<LabelEntity> labelEntity = labelRepository.findById(lb_id);
            if (labelEntity.isEmpty()) {
                throw new BadRequestError("Nhãn không tồn tại");
            }

            Optional<WorkingShiftEntity> workingShiftEntity = workingShiftRepository.findById(wks_id);
            if (workingShiftEntity.isEmpty()) {
                throw new BadRequestError("Ca làm việc không tồn tại");
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date ws_date = formatter.parse(createWorkScheduleDto.getWs_date());

            if (createWorkScheduleDto.getListEmployeeId().isEmpty()) {
                WorkScheduleEntity workScheduleEntity = WorkScheduleEntity.builder()
                        .label(labelEntity.get())
                        .workingShift(workingShiftEntity.get())
                        .ws_res_id(account.getAccountRestaurantId())
                        .ws_date(ws_date)
                        .ws_note(createWorkScheduleDto.getWs_note())
                        .listEmployeeId(null)
                        .createdBy(AccountUtils.convertAccountToJson(account))
                        .build();
                return workScheduleRepository.save(workScheduleEntity);
            } else {
                List<String> listAccountId = new ArrayList<>();
                for (String employeeId : createWorkScheduleDto.getListEmployeeId()) {
                    Employee.ReqFindOneEmployById request = Employee.ReqFindOneEmployById.newBuilder()
                            .setId(employeeId)
                            .setEplResId(account.getAccountRestaurantId())
                            .build();

                    Api.IBackendGRPC employee = grpcClient.getBlockingStub().findOneEmployeeById(request);

                    boolean status = employee.getStatus();
                    if (!status) {
                        throw new BadRequestError("Nhân viên không tồn tại");
                    }

                    String jsonData = employee.getData(); // giả sử đây là chuỗi JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    EmployeeModel employeeData = objectMapper.readValue(jsonData, EmployeeModel.class);

                    String employeeIdFromGrpc = employeeData.getAccountId();
                    listAccountId.add(employeeIdFromGrpc);
                }
                WorkScheduleEntity workScheduleEntity = WorkScheduleEntity.builder()
                        .label(labelEntity.get())
                        .workingShift(workingShiftEntity.get())
                        .ws_res_id(account.getAccountRestaurantId())
                        .ws_date(ws_date)
                        .ws_note(createWorkScheduleDto.getWs_note())
                        .listEmployeeId(createWorkScheduleDto.getListEmployeeId())
                        .createdBy(AccountUtils.convertAccountToJson(account))
                        .build();
               for (String accountId : listAccountId) {
                   //format date thành 12:30 - 2023-10-01
                   String date = new SimpleDateFormat("yyyy-MM-dd").format(ws_date);
                   String content = "Bạn vừa được phân công vào ca làm việc mới " + workingShiftEntity.get().getWks_name() + ":" + workingShiftEntity.get().getWks_start_time().replaceFirst(":00$", "") + " - " + workingShiftEntity.get().getWks_end_time().replaceFirst(":00$", "")+ " " + date;
                    CreateNotification createNotification = CreateNotification.builder()
                            .notiAccId(accountId)
                            .notiTitle("Lịch làm việc mới")
                            .notiContent(content)
                            .notiType("WORK_SCHEDULE")
                            .notiMetadata("no metadata")
                            .sendObject("work_schedule")
                            .build();
                        String json = new ObjectMapper().writeValueAsString(createNotification);
                        kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);
                }


                return workScheduleRepository.save(workScheduleEntity);
            }
        }
        catch (Exception e) {
            log.error("Error create work schedule: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkScheduleEntity getWorkSchedule(String ws_id,Account account) {
        try{
            UUID wsId = UUID.fromString(ws_id);
            Optional<WorkScheduleEntity> workScheduleEntity = workScheduleRepository.findByWsIdAndWsResId(wsId, account.getAccountRestaurantId());
            if (workScheduleEntity.isEmpty()) {
                throw new BadRequestError("Lịch làm việc không tồn tại");
            }
            return workScheduleEntity.get();
        }
        catch (Exception e) {
            log.error("Error get work schedule: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkScheduleEntity updateWorkSchedule(UpdateWorkScheduleDto updateWorkScheduleDto, Account account) {
        try{
            UUID ws_id = UUID.fromString(updateWorkScheduleDto.getWs_id());
            Optional<WorkScheduleEntity> workScheduleEntity = workScheduleRepository.findByWsIdAndWsResId(ws_id, account.getAccountRestaurantId());
            if (workScheduleEntity.isEmpty()) {
                throw new BadRequestError("Lịch làm việc không tồn tại");
            }

            UUID lb_id = UUID.fromString(updateWorkScheduleDto.getLb_id());
            Optional<LabelEntity> labelEntity = labelRepository.findById(lb_id);
            if (labelEntity.isEmpty()) {
                throw new BadRequestError("Nhãn không tồn tại");
            }

            UUID wks_id = UUID.fromString(updateWorkScheduleDto.getWks_id());
            Optional<WorkingShiftEntity> workingShiftEntity = workingShiftRepository.findById(wks_id);
            if (workingShiftEntity.isEmpty()) {
                throw new BadRequestError("Ca làm việc không tồn tại");
            }

            //chuyển đổi Date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date ws_date = formatter.parse(updateWorkScheduleDto.getWs_date());

            workScheduleEntity.get().setLabel(labelEntity.get());
            workScheduleEntity.get().setWorkingShift(workingShiftEntity.get());
            workScheduleEntity.get().setWs_date(ws_date);
            workScheduleEntity.get().setWs_note(updateWorkScheduleDto.getWs_note());
            workScheduleEntity.get().setUpdatedBy(AccountUtils.convertAccountToJson(account));
            if (updateWorkScheduleDto.getListEmployeeId().isEmpty()) {
                workScheduleEntity.get().setListEmployeeId(null);
            } else {
                workScheduleEntity.get().setListEmployeeId(updateWorkScheduleDto.getListEmployeeId());
                    List<String> listAccountId = new ArrayList<>();
                    for (String employeeId : updateWorkScheduleDto.getListEmployeeId()) {
                        Employee.ReqFindOneEmployById request = Employee.ReqFindOneEmployById.newBuilder()
                                .setId(employeeId)
                                .setEplResId(account.getAccountRestaurantId())
                                .build();

                        Api.IBackendGRPC employee = grpcClient.getBlockingStub().findOneEmployeeById(request);

                        boolean status = employee.getStatus();
                        if (!status) {
                            throw new BadRequestError("Nhân viên không tồn tại");
                        }

                        String jsonData = employee.getData(); // giả sử đây là chuỗi JSON
                        ObjectMapper objectMapper = new ObjectMapper();
                        EmployeeModel employeeData = objectMapper.readValue(jsonData, EmployeeModel.class);

                        String employeeIdFromGrpc = employeeData.getAccountId();
                        listAccountId.add(employeeIdFromGrpc);
                    }
                    for (String accountId : listAccountId) {
                        //format date thành 12:30 - 2023-10-01
                        String date = new SimpleDateFormat("yyyy-MM-dd").format(ws_date);
                        String content = "Bạn vừa được phân công vào ca làm việc mới " + workingShiftEntity.get().getWks_name() + ":" + workingShiftEntity.get().getWks_start_time().replaceFirst(":00$", "") + " - " + workingShiftEntity.get().getWks_end_time().replaceFirst(":00$", "")+ " " + date;
                        CreateNotification createNotification = CreateNotification.builder()
                                .notiAccId(accountId)
                                .notiTitle("Lịch làm việc mới")
                                .notiContent(content)
                                .notiType("WORK_SCHEDULE")
                                .notiMetadata("no metadata")
                                .sendObject("work_schedule")
                                .build();
                        String json = new ObjectMapper().writeValueAsString(createNotification);
                        kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);
                    }

                }
            return workScheduleRepository.save(workScheduleEntity.get());
        }
        catch (Exception e) {
            log.error("Error update work schedule: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkScheduleEntity deleteWorkSchedule(String ws_id, Account account) {
        try {
            UUID wsId = UUID.fromString(ws_id);
            Optional<WorkScheduleEntity> workScheduleEntity = workScheduleRepository.findByWsIdAndWsResId(wsId, account.getAccountRestaurantId());
            if (workScheduleEntity.isEmpty()) {
                throw new BadRequestError("Lịch làm việc không tồn tại");
            }
            workScheduleEntity.get().setIsDeleted(1);
            workScheduleEntity.get().setDeletedAt(new Date());
            workScheduleEntity.get().setDeletedBy(AccountUtils.convertAccountToJson(account));
            return workScheduleRepository.save(workScheduleEntity.get());
        }
        catch (Exception e) {
            log.error("Error delete work schedule: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkScheduleEntity restoreWorkSchedule(String ws_id, Account account) {
        try {
            UUID wsId = UUID.fromString(ws_id);
            Optional<WorkScheduleEntity> workScheduleEntity = workScheduleRepository.findByWsIdAndWsResId(wsId, account.getAccountRestaurantId());
            if (workScheduleEntity.isEmpty()) {
                throw new BadRequestError("Lịch làm việc không tồn tại");
            }
            workScheduleEntity.get().setIsDeleted(0);
            workScheduleEntity.get().setDeletedAt(null);
            workScheduleEntity.get().setDeletedBy(null);
            return workScheduleRepository.save(workScheduleEntity.get());
        }
        catch (Exception e) {
            log.error("Error restore work schedule: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkScheduleEntity> getListWorkSchedule(String start_date, String end_date, Account account) {
       try {

           start_date = start_date.replaceAll("\\s*\\(.*\\)$", "");
           end_date = end_date.replaceAll("\\s*\\(.*\\)$", "");

           // Định dạng phù hợp với chuỗi
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'XX", Locale.ENGLISH);

           // Chuyển đổi sang OffsetDateTime
           OffsetDateTime startDate = OffsetDateTime.parse(start_date, formatter);
           OffsetDateTime endDate = OffsetDateTime.parse(end_date, formatter);

           // Chuyển sang Date
           Date startDateConvert = Date.from(startDate.toInstant());
           Date endDateConvert = Date.from(endDate.toInstant());
           return workScheduleRepository.findByWsResIdAndIsDeletedAndWsDateBetween(account.getAccountRestaurantId(),0, startDateConvert, endDateConvert);
              //return null;
       }
       catch (Exception e) {
           log.error("Error get list work schedule: ", e);
           throw new RuntimeException(e);
       }
    }

    @Override
    public List<String> getListEmployeAssignedByDate(String selectedDate, Account account) {
        try {
            selectedDate = selectedDate.replaceAll("\\s*\\(.*\\)$", "");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'XX", Locale.ENGLISH);
            OffsetDateTime startDate = OffsetDateTime.parse(selectedDate, formatter);
            Date startDateConvert = Date.from(startDate.toInstant());
            List<WorkScheduleEntity> workSchedules = workScheduleRepository.DateWorkExist(
                    account.getAccountRestaurantId(),
                    startDateConvert
            );

            return workSchedules.stream()
                    .flatMap(schedule -> schedule.getListEmployeeId().stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            log.error("Error get list employee assigned by date: ", e);
            throw new RuntimeException(e);
        }
    }
}
