package com.pg.employee.service;

import com.pg.employee.dto.request.leaveApplication.CreateLeaveApplicationDto;
import com.pg.employee.dto.request.leaveApplication.RestaurantLeaveApplicationDto;
import com.pg.employee.dto.request.leaveApplication.UpdateLeaveApplicationDto;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.LeaveApplicationEntity;
import com.pg.employee.middleware.Account;
import com.pg.employee.repository.LeaveApplicationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface LeaveApplicationService {

    LeaveApplicationEntity createLeaveApplication(CreateLeaveApplicationDto createLeaveApplicationDto, Account account);
    LeaveApplicationEntity getLeaveApplicationByEmployee(String id, Account account);
    LeaveApplicationEntity getLeaveApplicationByRestaurant(String id, Account account);
    LeaveApplicationEntity updateLeaveApplication(UpdateLeaveApplicationDto updateLeaveApplicationDto, Account account);
    LeaveApplicationEntity deleteLeaveApplication(String id, Account account);
    ResPagination<LeaveApplicationEntity> getAllLeaveApplication(int pageIndex, int pageSize, String leaveType,String status, Account account);
    ResPagination<LeaveApplicationEntity> getAllLeaveApplicationByEmployee(int pageIndex, int pageSize, String leaveType,String status, Account account);
    LeaveApplicationEntity sendLeaveApplication(String id, Account account);
    LeaveApplicationEntity approveLeaveApplication(RestaurantLeaveApplicationDto restaurantLeaveApplicationDto,Account account);
    LeaveApplicationEntity rejectLeaveApplication(RestaurantLeaveApplicationDto restaurantLeaveApplicationDto, Account account);
    LeaveApplicationEntity cancelLeaveApplication(String id, Account account);

}
