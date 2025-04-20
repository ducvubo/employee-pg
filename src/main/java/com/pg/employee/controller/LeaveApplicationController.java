package com.pg.employee.controller;

import com.pg.employee.dto.request.leaveApplication.CreateLeaveApplicationDto;
import com.pg.employee.dto.request.leaveApplication.RestaurantLeaveApplicationDto;
import com.pg.employee.dto.request.leaveApplication.UpdateLeaveApplicationDto;
import com.pg.employee.dto.response.ApiResponse;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.LeaveApplicationEntity;
import com.pg.employee.entities.WorkingShiftEntity;
import com.pg.employee.middleware.Account;
import com.pg.employee.service.LabelService;
import com.pg.employee.service.LeaveApplicationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave-application")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @PostMapping
    ApiResponse<LeaveApplicationEntity> createLeaveApplication(@Valid @RequestBody CreateLeaveApplicationDto createLeaveApplicationDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Tạo đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.createLeaveApplication(createLeaveApplicationDto,account))
                .build();
    }

    @PatchMapping
    ApiResponse<LeaveApplicationEntity> updateLeaveApplication(@Valid @RequestBody UpdateLeaveApplicationDto updateLeaveApplicationDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Cập nhật đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.updateLeaveApplication(updateLeaveApplicationDto,account))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<LeaveApplicationEntity> deleteLeaveApplication(@PathVariable String id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Xóa đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.deleteLeaveApplication(id,account))
                .build();
    }

    @GetMapping("/get-with-employee/{id}")
    ApiResponse<LeaveApplicationEntity> getLeaveApplicationByEmployee(@PathVariable String id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Lấy đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.getLeaveApplicationByEmployee(id,account))
                .build();
    }

    @GetMapping("/get-with-restaurant/{id}")
    ApiResponse<LeaveApplicationEntity> getLeaveApplicationByRestaurant(@PathVariable String id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Lấy đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.getLeaveApplicationByRestaurant(id,account))
                .build();
    }

    @GetMapping("/get-all-with-employee")
    ApiResponse<ResPagination<LeaveApplicationEntity>> getAllLeaveApplicationByEmployee(@RequestParam int pageIndex, @RequestParam int pageSize, @RequestParam(required = false) String leaveType, @RequestParam(required = false) String status) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<ResPagination<LeaveApplicationEntity>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Lấy danh sách đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.getAllLeaveApplicationByEmployee(pageIndex, pageSize, leaveType, status, account))
                .build();
    }

    @GetMapping("/get-all-with-restaurant")
    ApiResponse<ResPagination<LeaveApplicationEntity>> getAllLeaveApplication(@RequestParam int pageIndex, @RequestParam int pageSize, @RequestParam(required = false) String leaveType, @RequestParam(required = false) String status) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<ResPagination<LeaveApplicationEntity>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Lấy danh sách đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.getAllLeaveApplication(pageIndex, pageSize, leaveType, status, account))
                .build();
    }

    @PatchMapping("/send/{id}")
    ApiResponse<LeaveApplicationEntity> sendLeaveApplication(@PathVariable String id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Gửi đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.sendLeaveApplication(id,account))
                .build();
    }

    @PatchMapping("/approve")
    ApiResponse<LeaveApplicationEntity> approveLeaveApplication(@Valid @RequestBody RestaurantLeaveApplicationDto restaurantLeaveApplicationDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Phê duyệt đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.approveLeaveApplication(restaurantLeaveApplicationDto,account))
                .build();
    }

    @PatchMapping("/reject")
    ApiResponse<LeaveApplicationEntity> rejectLeaveApplication(@Valid @RequestBody RestaurantLeaveApplicationDto restaurantLeaveApplicationDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Từ chối đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.rejectLeaveApplication(restaurantLeaveApplicationDto,account))
                .build();
    }

    @PatchMapping("/cancel/{id}")
    ApiResponse<LeaveApplicationEntity> cancelLeaveApplication(@PathVariable String id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<LeaveApplicationEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Hủy đơn xin nghỉ phép thành công")
                .data(leaveApplicationService.cancelLeaveApplication(id,account))
                .build();
    }
}
