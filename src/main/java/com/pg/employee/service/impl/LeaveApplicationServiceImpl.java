package com.pg.employee.service.impl;

import com.pg.employee.dto.request.leaveApplication.CreateLeaveApplicationDto;
import com.pg.employee.dto.request.leaveApplication.RestaurantLeaveApplicationDto;
import com.pg.employee.dto.request.leaveApplication.UpdateLeaveApplicationDto;
import com.pg.employee.dto.response.MetaPagination;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.LabelEntity;
import com.pg.employee.entities.LeaveApplicationEntity;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import com.pg.employee.repository.LeaveApplicationRepository;
import com.pg.employee.repository.WorkingShiftRepository;
import com.pg.employee.service.LeaveApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class LeaveApplicationServiceImpl implements LeaveApplicationService {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;


    @Override
    public LeaveApplicationEntity createLeaveApplication(CreateLeaveApplicationDto createLeaveApplicationDto, Account account) {
        LeaveApplicationEntity leaveApplicationEntity = new LeaveApplicationEntity();
        leaveApplicationEntity.setLeaveType(createLeaveApplicationDto.getLeaveType());
        leaveApplicationEntity.setStartDate(createLeaveApplicationDto.getStartDate());
        leaveApplicationEntity.setEndDate(createLeaveApplicationDto.getEndDate());
        leaveApplicationEntity.setReason(createLeaveApplicationDto.getReason());
        leaveApplicationEntity.setStatus("DRAFT");
        leaveApplicationEntity.setEmployeeId(account.getAccountEmployeeId());
        leaveApplicationEntity.setLeaveAppResId(account.getAccountRestaurantId());

        leaveApplicationRepository.save(leaveApplicationEntity);

        return leaveApplicationEntity;
    }

    @Override
    public LeaveApplicationEntity getLeaveApplicationByEmployee(String id, Account account) {
        if (id == null || id.isEmpty()) {
            throw new BadRequestError("Vui lòng nhập id đơn xin nghỉ");
        }
        //ép qua UUID
        UUID uuid = UUID.fromString(id);
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndEmployeeId(uuid, account.getAccountEmployeeId());

        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }
        return leaveApplicationEntity;
    }

    @Override
    public LeaveApplicationEntity getLeaveApplicationByRestaurant(String id, Account account) {
        if (id == null || id.isEmpty()) {
            throw new BadRequestError("Vui lòng nhập id đơn xin nghỉ");
        }
        //ép qua UUID
        UUID uuid = UUID.fromString(id);
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndLeaveAppResId(uuid, account.getAccountRestaurantId());

        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }
        return leaveApplicationEntity;
    }

    @Override
    public LeaveApplicationEntity updateLeaveApplication(UpdateLeaveApplicationDto updateLeaveApplicationDto, Account account) {
        UUID uuid = UUID.fromString(updateLeaveApplicationDto.getLeaveAppId());
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndEmployeeId(uuid, account.getAccountEmployeeId());
        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }

        if (!leaveApplicationEntity.getStatus().equals("DRAFT")) {
            throw new BadRequestError("Không thể sửa đơn xin nghỉ này vì nó không ở trạng thái nháp");
        }

        leaveApplicationEntity.setLeaveType(updateLeaveApplicationDto.getLeaveType());
        leaveApplicationEntity.setStartDate(updateLeaveApplicationDto.getStartDate());
        leaveApplicationEntity.setEndDate(updateLeaveApplicationDto.getEndDate());
        leaveApplicationEntity.setReason(updateLeaveApplicationDto.getReason());
        leaveApplicationEntity.setLeaveAppResId(account.getAccountRestaurantId());
        leaveApplicationRepository.save(leaveApplicationEntity);

        return leaveApplicationEntity;
    }

    @Override
    public LeaveApplicationEntity deleteLeaveApplication(String id, Account account) {
        if (id == null || id.isEmpty()) {
            throw new BadRequestError("Vui lòng nhập id đơn xin nghỉ");
        }
        //ép qua UUID
        UUID uuid = UUID.fromString(id);
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndEmployeeId(uuid, account.getAccountEmployeeId());
        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }

        String status = leaveApplicationEntity.getStatus();
        if (!status.equals("DRAFT") && !status.equals("CANCEL")) {
            throw new BadRequestError("Chỉ có thể xóa đơn xin nghỉ ở trạng thái nháp hoặc hủy");
        }


        leaveApplicationRepository.delete(leaveApplicationEntity);
        return leaveApplicationEntity;
    }

    @Override
    public ResPagination<LeaveApplicationEntity> getAllLeaveApplication(int pageIndex, int pageSize, String leaveType, String status, Account account) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);

        if (account.getAccountEmployeeId() == null || account.getAccountEmployeeId().isEmpty()) {
            Page<LeaveApplicationEntity> page = leaveApplicationRepository.findAllByFilter(
                    account.getAccountRestaurantId(),
                    leaveType,
                    status,
                    pageable
            );

            return ResPagination.<LeaveApplicationEntity>builder()
                    .result(page.getContent())
                    .meta(MetaPagination.builder()
                            .current(pageIndex)
                            .pageSize(pageSize)
                            .totalPage(page.getTotalPages())
                            .totalItem(page.getTotalElements())
                            .build()).build();

        }else {
            return ResPagination.<LeaveApplicationEntity>builder()
                    .result(Arrays.asList())
                    .meta(MetaPagination.builder()
                            .current(pageIndex)
                            .pageSize(pageSize)
                            .totalPage(0)
                            .totalItem(0L)
                            .build()).build();
        }


    }

    @Override
    public ResPagination<LeaveApplicationEntity> getAllLeaveApplicationByEmployee(int pageIndex, int pageSize, String leaveType, String status, Account account) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);

        Page<LeaveApplicationEntity> page = leaveApplicationRepository
                .getAllLeaveApplicationByEmployee(
                        account.getAccountEmployeeId(), // lấy ID nhân viên từ account
                        leaveType,
                        status,
                        pageable
                );
        return ResPagination.<LeaveApplicationEntity>builder()
                .result(page.getContent())
                .meta(MetaPagination.builder()
                        .current(pageIndex)
                        .pageSize(pageSize)
                        .totalPage(page.getTotalPages())
                        .totalItem(page.getTotalElements())
                        .build()).build();
    }

    @Override
    public LeaveApplicationEntity sendLeaveApplication(String id, Account account) {
        if (id == null || id.isEmpty()) {
            throw new BadRequestError("Vui lòng nhập id đơn xin nghỉ");
        }
        UUID uuid = UUID.fromString(id);
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndEmployeeId(uuid, account.getAccountEmployeeId());
        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }

        if (!leaveApplicationEntity.getStatus().equals("DRAFT")) {
            throw new BadRequestError("Không thể gửi đơn xin nghỉ này vì nó không ở trạng thái nháp");
        }

        leaveApplicationEntity.setStatus("PENDING");
        leaveApplicationRepository.save(leaveApplicationEntity);

        return leaveApplicationEntity;
    }

    @Override
    public LeaveApplicationEntity approveLeaveApplication(RestaurantLeaveApplicationDto restaurantLeaveApplicationDto, Account account) {
        if (restaurantLeaveApplicationDto.getLeaveAppId() == null || restaurantLeaveApplicationDto.getLeaveAppId().isEmpty()) {
            throw new BadRequestError("Vui lòng nhập id đơn xin nghỉ");
        }

        UUID uuid = UUID.fromString(restaurantLeaveApplicationDto.getLeaveAppId());
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndLeaveAppResId(uuid, account.getAccountRestaurantId());
        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }

        if (!leaveApplicationEntity.getStatus().equals("PENDING")) {
            throw new BadRequestError("Không thể duyệt đơn xin nghỉ này vì nó không ở trạng thái chờ duyệt");
        }

        leaveApplicationEntity.setApprovalComment(restaurantLeaveApplicationDto.getApprovalComment());
        leaveApplicationEntity.setApprovedAt(new Date());
        leaveApplicationEntity.setStatus("APPROVED");
        leaveApplicationEntity.setApprovedBy(account.getId());
        leaveApplicationRepository.save(leaveApplicationEntity);

        return leaveApplicationEntity;
    }

    @Override
    public LeaveApplicationEntity rejectLeaveApplication(RestaurantLeaveApplicationDto restaurantLeaveApplicationDto, Account account) {
        if (restaurantLeaveApplicationDto.getLeaveAppId() == null || restaurantLeaveApplicationDto.getLeaveAppId().isEmpty()) {
            throw new BadRequestError("Vui lòng nhập id đơn xin nghỉ");
        }
        UUID uuid = UUID.fromString(restaurantLeaveApplicationDto.getLeaveAppId());
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndLeaveAppResId(uuid, account.getAccountRestaurantId());
        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }

        if (!leaveApplicationEntity.getStatus().equals("PENDING")) {
            throw new BadRequestError("Không thể từ chối đơn xin nghỉ này vì nó không ở trạng thái chờ duyệt");
        }

        leaveApplicationEntity.setApprovalComment(restaurantLeaveApplicationDto.getApprovalComment());
        leaveApplicationEntity.setApprovedAt(new Date());
        leaveApplicationEntity.setStatus("REJECTED");
        leaveApplicationEntity.setApprovedBy(account.getAccountEmployeeId());
        leaveApplicationRepository.save(leaveApplicationEntity);

        return leaveApplicationEntity;
    }

    @Override
    public LeaveApplicationEntity cancelLeaveApplication(String id, Account account) {
       //chỉ hủy khi ở pending
        if (id == null || id.isEmpty()) {
            throw new BadRequestError("Vui lòng nhập id đơn xin nghỉ");
        }
        UUID uuid = UUID.fromString(id);
        LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByLeaveAppIdAndEmployeeId(uuid, account.getAccountEmployeeId());
        if (leaveApplicationEntity == null) {
            throw new BadRequestError("Không tìm thấy đơn xin nghỉ nào với id này");
        }

        if (!leaveApplicationEntity.getStatus().equals("PENDING")) {
            throw new BadRequestError("Không thể hủy đơn xin nghỉ này vì nó không ở trạng thái chờ duyệt");
        }

        leaveApplicationEntity.setStatus("CANCELED");
        leaveApplicationRepository.save(leaveApplicationEntity);

        return leaveApplicationEntity;
    }
}
