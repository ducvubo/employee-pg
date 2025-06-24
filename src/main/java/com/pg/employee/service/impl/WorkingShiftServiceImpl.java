package com.pg.employee.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.employee.dto.request.workingShift.CreateWorkingShiftDto;
import com.pg.employee.dto.request.workingShift.UpdateWorkingShiftDto;
import com.pg.employee.dto.response.MetaPagination;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.WorkingShiftEntity;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import com.pg.employee.models.CreateNotificationRestaurant;
import com.pg.employee.repository.LabelRepository;
import com.pg.employee.repository.WorkingShiftRepository;
import com.pg.employee.service.WorkingShiftService;
import com.pg.employee.utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class WorkingShiftServiceImpl implements WorkingShiftService {

    @Autowired
    private WorkingShiftRepository workingShiftRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Override
    public WorkingShiftEntity createWorkingShift(CreateWorkingShiftDto createWorkingShiftDto, Account account) {
        try{
            WorkingShiftEntity workingShiftEntity = WorkingShiftEntity.builder()
                    .wks_name(createWorkingShiftDto.getWks_name())
                    .wks_description(createWorkingShiftDto.getWks_description())
                    .wks_start_time(createWorkingShiftDto.getWks_start_time())
                    .wks_end_time(createWorkingShiftDto.getWks_end_time())
                    .wks_res_id(account.getAccountRestaurantId())
                    .createdBy(AccountUtils.convertAccountToJson(account))
                    .updatedBy(AccountUtils.convertAccountToJson(account))
                    .isDeleted(0)
                    .build();
            workingShiftRepository.save(workingShiftEntity);

            CreateNotificationRestaurant createNotification = CreateNotificationRestaurant.builder()
                    .restaurantId(account.getAccountRestaurantId())
                    .notiTitle("Ca làm việc mới")
                    .notiContent("Ca làm việc mới '" + createWorkingShiftDto.getWks_name())
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);
            return workingShiftEntity;
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }


    }

    @Override
    public WorkingShiftEntity getWorkingShift(String id) {
        try {
         UUID wks_id = UUID.fromString(id);
            Optional<WorkingShiftEntity> workingShiftEntity = workingShiftRepository.findById(wks_id);
            if (workingShiftEntity.isEmpty()) {
                throw new BadRequestError("Ca làm việc không tồn tại");
            }
            return workingShiftEntity.get();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkingShiftEntity updateWorkingShift(UpdateWorkingShiftDto updateWorkingShiftDto, Account account) {
        try{
            Optional<WorkingShiftEntity> workingShiftEntity = workingShiftRepository.findById(UUID.fromString(updateWorkingShiftDto.getWks_id()));
            if (workingShiftEntity.isEmpty()) {
                throw new BadRequestError("Ca làm việc không tồn tại");
            }

            workingShiftEntity.get().setWks_name(updateWorkingShiftDto.getWks_name());
            workingShiftEntity.get().setWks_description(updateWorkingShiftDto.getWks_description());
            workingShiftEntity.get().setWks_start_time(updateWorkingShiftDto.getWks_start_time());
            workingShiftEntity.get().setWks_end_time(updateWorkingShiftDto.getWks_end_time());
            workingShiftEntity.get().setUpdatedBy(AccountUtils.convertAccountToJson(account));
            workingShiftRepository.save(workingShiftEntity.get());

            CreateNotificationRestaurant createNotification = CreateNotificationRestaurant.builder()
                    .restaurantId(account.getAccountRestaurantId())
                    .notiTitle("Cập nhật ca làm việc")
                    .notiContent("Ca làm việc '" + updateWorkingShiftDto.getWks_name() + "' đã được cập nhật")
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);

            return workingShiftEntity.get();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkingShiftEntity deleteWorkingShift(String id, Account account) {
        try {
            UUID wks_id = UUID.fromString(id);
            Optional<WorkingShiftEntity> workingShiftEntity = workingShiftRepository.findById(wks_id);
            if (workingShiftEntity.isEmpty()) {
                throw new BadRequestError("Ca làm việc không tồn tại");
            }
            workingShiftEntity.get().setIsDeleted(1);
            workingShiftEntity.get().setDeletedBy(AccountUtils.convertAccountToJson(account));
            workingShiftEntity.get().setDeletedAt(new Date(System.currentTimeMillis()));
            workingShiftRepository.save(workingShiftEntity.get());

            CreateNotificationRestaurant createNotification = CreateNotificationRestaurant.builder()
                    .restaurantId(account.getAccountRestaurantId())
                    .notiTitle("Xóa ca làm việc")
                    .notiContent("Ca làm việc '" + workingShiftEntity.get().getWks_name() + "' đã được xóa")
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);

            return workingShiftEntity.get();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WorkingShiftEntity restoreWorkingShift(String id, Account account) {
        try {
            UUID wks_id = UUID.fromString(id);
            Optional<WorkingShiftEntity> workingShiftEntity = workingShiftRepository.findById(wks_id);
            if (workingShiftEntity.isEmpty()) {
                throw new BadRequestError("Ca làm việc không tồn tại");
            }
            workingShiftEntity.get().setIsDeleted(0);
            workingShiftEntity.get().setDeletedBy(null);
            workingShiftEntity.get().setDeletedAt(null);
            workingShiftRepository.save(workingShiftEntity.get());

            CreateNotificationRestaurant createNotification = CreateNotificationRestaurant.builder()
                    .restaurantId(account.getAccountRestaurantId())
                    .notiTitle("Khôi phục ca làm việc")
                    .notiContent("Ca làm việc '" + workingShiftEntity.get().getWks_name() + "' đã được khôi phục")
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);

            return workingShiftEntity.get();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResPagination<WorkingShiftEntity> getAllWorkingShift(int pageIndex, int pageSize, String wks_name, Account account) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
            Page<WorkingShiftEntity> workingShift = workingShiftRepository.findByFilters(wks_name, account.getAccountRestaurantId(), 0, pageable);

            return ResPagination.<WorkingShiftEntity>builder()
                    .result(workingShift.getContent())
                    .meta(MetaPagination.builder()
                            .current(pageIndex)
                            .pageSize(pageSize)
                            .totalPage(workingShift.getTotalPages())
                            .totalItem(workingShift.getTotalElements())
                            .build())
                    .build();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResPagination<WorkingShiftEntity> getAllWorkingShiftRecycleBin(int pageIndex, int pageSize, String wks_name, Account account) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
            Page<WorkingShiftEntity> workingShift = workingShiftRepository.findByFilters(wks_name, account.getAccountRestaurantId(), 1, pageable);

            return ResPagination.<WorkingShiftEntity>builder()
                    .result(workingShift.getContent())
                    .meta(MetaPagination.builder()
                            .current(pageIndex)
                            .pageSize(pageSize)
                            .totalPage(workingShift.getTotalPages())
                            .totalItem(workingShift.getTotalElements())
                            .build())
                    .build();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkingShiftEntity> getAllWorkingShiftByRestaurantId(Account account) {
        try {
            return workingShiftRepository.findAllByRestaurantId(account.getAccountRestaurantId());
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }
}
