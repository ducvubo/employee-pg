package com.pg.employee.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.employee.dto.request.label.CreateLabelDto;
import com.pg.employee.dto.request.label.UpdateLabelDto;
import com.pg.employee.dto.request.label.UpdateStatusLabelDto;
import com.pg.employee.dto.response.MetaPagination;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.LabelEntity;
import com.pg.employee.enums.EnumStatus;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import com.pg.employee.models.CreateNotification;
import com.pg.employee.repository.LabelRepository;
import com.pg.employee.service.LabelService;
import com.pg.employee.utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaAdmin kafkaAdmin;


    @Override
    public LabelEntity createLabel(CreateLabelDto createLabelDto, Account account) {
        try{
            LabelEntity labelEntity = LabelEntity.builder()
                    .lb_name(createLabelDto.getLb_name())
                    .lb_description(createLabelDto.getLb_description())
                    .lb_color(createLabelDto.getLb_color())
                    .lb_res_id(account.getAccountRestaurantId())
                    .lb_status(EnumStatus.ENABLED)
                    .createdBy(AccountUtils.convertAccountToJson(account))
                    .updatedBy(AccountUtils.convertAccountToJson(account))
                    .isDeleted(0)
                    .build();

            labelRepository.save(labelEntity);

            CreateNotification createNotification = CreateNotification.builder()
                    .notiAccId(account.getAccountRestaurantId())
                    .notiTitle("Nhãn làm việc mới")
                    .notiContent("Nhãn làm việc mới '" + createLabelDto.getLb_name())
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);

            return labelEntity;
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabelEntity getLabel(String id) {
        try {
            UUID lb_id = UUID.fromString(id);
            Optional<LabelEntity> labelEntity = labelRepository.findById(lb_id);
            if (labelEntity.isEmpty()) {
                throw new BadRequestError("Label not found");
            }
            return labelEntity.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabelEntity updateLabel(UpdateLabelDto updateLabelDto, Account account) {
        try {
            UUID id = UUID.fromString(updateLabelDto.getLb_id());

            // Update label
            Optional<LabelEntity> labelEntity = labelRepository.findById(id);
            if (labelEntity.isEmpty()) {
                throw new BadRequestError("Label not found");
            }
            labelEntity.get().setLb_name(updateLabelDto.getLb_name());
            labelEntity.get().setLb_description(updateLabelDto.getLb_description());
            labelEntity.get().setLb_color(updateLabelDto.getLb_color());
            labelEntity.get().setUpdatedBy(AccountUtils.convertAccountToJson(account));
            labelRepository.save(labelEntity.get());

            CreateNotification createNotification = CreateNotification.builder()
                    .notiAccId(account.getAccountRestaurantId())
                    .notiTitle("Nhãn làm việc cập nhật")
                    .notiContent("Nhãn làm việc '" + updateLabelDto.getLb_name() + "' đã được cập nhật")
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);

            return labelEntity.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabelEntity deleteLabel(String id, Account account) {
        try{
            UUID labelId = UUID.fromString(id);
            Optional<LabelEntity> labelEntity = labelRepository.findById(labelId);
            if (labelEntity.isEmpty()) {
                throw new BadRequestError("Label not found");
            }
            //update isDeleted = 1
            labelEntity.get().setIsDeleted(1);
            labelEntity.get().setDeletedBy(AccountUtils.convertAccountToJson(account));
            labelEntity.get().setDeletedAt(new Date(System.currentTimeMillis()));
            labelRepository.save(labelEntity.get());
            CreateNotification createNotification = CreateNotification.builder()
                    .notiAccId(account.getAccountRestaurantId())
                    .notiTitle("Nhãn làm việc đã xóa")
                    .notiContent("Nhãn làm việc '" + labelEntity.get().getLb_name() + "' đã được xóa")
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);
            return labelEntity.get();
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabelEntity restoreLabel(String id, Account account) {
        try {
            UUID lb_id = UUID.fromString(id);
            Optional<LabelEntity> labelEntity = labelRepository.findById(lb_id);
            if (labelEntity.isEmpty()) {
                throw new BadRequestError("Label not found");
            }
            labelEntity.get().setIsDeleted(0);
            labelEntity.get().setDeletedBy(null);
            labelEntity.get().setDeletedAt(null);
            labelRepository.save(labelEntity.get());
            CreateNotification createNotification = CreateNotification.builder()
                    .notiAccId(account.getAccountRestaurantId())
                    .notiTitle("Nhãn làm việc đã khôi phục")
                    .notiContent("Nhãn làm việc '" + labelEntity.get().getLb_name() + "' đã được khôi phục")
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);
            return labelEntity.get();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public LabelEntity updateStatus(UpdateStatusLabelDto updateStatusLabelDto, Account account) {
        try {
            UUID id = UUID.fromString(updateStatusLabelDto.getLb_id());

            // Update label
            Optional<LabelEntity> labelEntity = labelRepository.findById(id);
            if (labelEntity.isEmpty()) {
                throw new BadRequestError("Label not found");
            }
            labelEntity.get().setLb_status(updateStatusLabelDto.getLb_status());
            labelEntity.get().setUpdatedBy(AccountUtils.convertAccountToJson(account));
            labelRepository.save(labelEntity.get());

            CreateNotification createNotification = CreateNotification.builder()
                    .notiAccId(account.getAccountRestaurantId())
                    .notiTitle("Nhãn làm việc cập nhật trạng thái")
                    .notiContent("Nhãn làm việc '" + labelEntity.get().getLb_name() + "' đã được cập nhật trạng thái")
                    .notiType("label")
                    .notiMetadata("no metadata")
                    .sendObject("all_account")
                    .build();
            String json = new ObjectMapper().writeValueAsString(createNotification);
            kafkaTemplate.send("NOTIFICATION_ACCOUNT_CREATE", json);

            return labelEntity.get();
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResPagination<LabelEntity> getAllLabel(int pageIndex, int pageSize, String lb_name, Account account) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
            Page<LabelEntity> labelEntities = labelRepository.findByFilters(lb_name, account.getAccountRestaurantId(), 0, pageable);

            return ResPagination.<LabelEntity>builder()
                    .result(labelEntities.getContent())
                    .meta(MetaPagination.builder()
                            .current(pageIndex)
                            .pageSize(pageSize)
                            .totalPage(labelEntities.getTotalPages())
                            .totalItem(labelEntities.getTotalElements())
                            .build())
                    .build();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResPagination<LabelEntity> getAllLabelRecycleBin(int pageIndex, int pageSize, String lb_name, Account account) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
            Page<LabelEntity> labelEntities = labelRepository.findByFilters(lb_name, account.getAccountRestaurantId(), 1, pageable);

            return ResPagination.<LabelEntity>builder()
                    .result(labelEntities.getContent())
                    .meta(MetaPagination.builder()
                            .current(pageIndex)
                            .pageSize(pageSize)
                            .totalPage(labelEntities.getTotalPages())
                            .totalItem(labelEntities.getTotalElements())
                            .build())
                    .build();
        }catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<LabelEntity> getAllLabelByRestaurantId(Account account) {
        try {
            return labelRepository.findAllByRestaurantId(account.getAccountRestaurantId());
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }


}
