package com.pg.employee.controller;


import com.pg.employee.dto.request.workingShift.CreateWorkingShiftDto;
import com.pg.employee.dto.request.workingShift.UpdateWorkingShiftDto;
import com.pg.employee.dto.response.ApiResponse;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.WorkingShiftEntity;
import com.pg.employee.middleware.Account;
import com.pg.employee.service.WorkingShiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/working-shift")
@RequiredArgsConstructor
public class WorkingShiftController {

    @Autowired
    private WorkingShiftService workingShiftService;

    @PostMapping
    ApiResponse<WorkingShiftEntity> createWorkingShift(@Valid @RequestBody CreateWorkingShiftDto createWorkingShiftDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkingShiftEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create workingShift successfully")
                .data(workingShiftService.createWorkingShift(createWorkingShiftDto,account))
                .build();
    }

    @PatchMapping
    ApiResponse<WorkingShiftEntity> updateWorkingShift(@Valid @RequestBody UpdateWorkingShiftDto updateWorkingShiftDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkingShiftEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Update workingShift successfully")
                .data(workingShiftService.updateWorkingShift(updateWorkingShiftDto,account))
                .build();
    }

    @GetMapping
    ApiResponse<ResPagination<WorkingShiftEntity>> getWorkingShifts(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                      @RequestParam(value = "wks_name", required = false) String wks_name) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<ResPagination<WorkingShiftEntity>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get workingShifts successfully")
                .data(workingShiftService.getAllWorkingShift(pageIndex, pageSize, wks_name, account))
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<WorkingShiftEntity>> getAllWorkingShifts() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<List<WorkingShiftEntity>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get workingShifts successfully")
                .data(workingShiftService.getAllWorkingShiftByRestaurantId(account))
                .build();
    }

    @GetMapping("recycle")
    ApiResponse<ResPagination<WorkingShiftEntity>> getRecycleWorkingShifts(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                             @RequestParam(value = "wks_name", required = false) String wks_name) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<ResPagination<WorkingShiftEntity>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get recycle workingShifts successfully")
                .data(workingShiftService.getAllWorkingShiftRecycleBin( pageIndex, pageSize, wks_name, account))
                .build();
    }

    @PatchMapping("/restore/{wks_id}")
    ApiResponse<WorkingShiftEntity> restoreWorkingShift(@PathVariable String wks_id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkingShiftEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Restore workingShift successfully")
                .data(workingShiftService.restoreWorkingShift(wks_id, account))
                .build();
    }

    @DeleteMapping("/{wks_id}")
    ApiResponse<WorkingShiftEntity> deleteWorkingShift(@PathVariable String wks_id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkingShiftEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Delete workingShift successfully")
                .data(workingShiftService.deleteWorkingShift(wks_id, account))
                .build();
    }

    @GetMapping("/{wks_id}")
    ApiResponse<WorkingShiftEntity> getWorkingShift(@PathVariable String wks_id) {
        return ApiResponse.<WorkingShiftEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get workingShift successfully")
                .data(workingShiftService.getWorkingShift(wks_id))
                .build();
    }
}
