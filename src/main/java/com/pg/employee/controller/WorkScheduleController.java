package com.pg.employee.controller;

import com.pg.employee.dto.request.workSchedule.CreateWorkScheduleDto;
import com.pg.employee.dto.request.workSchedule.UpdateWorkScheduleDto;
import com.pg.employee.dto.response.ApiResponse;
import com.pg.employee.entities.WorkScheduleEntity;
import com.pg.employee.middleware.Account;
import com.pg.employee.service.WorkScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/work-schedule")
@RequiredArgsConstructor
public class WorkScheduleController {

    @Autowired
    private WorkScheduleService workScheduleService;

    @PostMapping
    ApiResponse<WorkScheduleEntity> createWorkSchedule(@Valid @RequestBody CreateWorkScheduleDto createWorkScheduleDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkScheduleEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create work schedule successfully")
                .data(workScheduleService.createWorkSchedule(createWorkScheduleDto, account))
                .build();
    }

    @GetMapping
    ApiResponse<List<WorkScheduleEntity>> getWorkSchedules(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate
    ) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<WorkScheduleEntity> workSchedules = workScheduleService.getListWorkSchedule(startDate, endDate, account);

        return ApiResponse.<List<WorkScheduleEntity>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get work schedules successfully")
                .data(workSchedules)
                .build();
    }

    @GetMapping("/list-employee-by-date/{date}")
    ApiResponse<List<String>> getListEmployeeByDate(@PathVariable String date) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<List<String>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get list employee by date successfully")
                .data(workScheduleService.getListEmployeAssignedByDate(date, account))
                .build();
    }

    @PatchMapping
    ApiResponse<WorkScheduleEntity> updateWorkSchedule(@Valid @RequestBody UpdateWorkScheduleDto updateWorkScheduleDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkScheduleEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Update work schedule successfully")
                .data(workScheduleService.updateWorkSchedule(updateWorkScheduleDto, account))
                .build();
    }

    @PatchMapping("restore/{ws_id}")
    ApiResponse<WorkScheduleEntity> restoreWorkSchedule(@PathVariable String ws_id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkScheduleEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Restore work schedule successfully")
                .data(workScheduleService.restoreWorkSchedule(ws_id, account))
                .build();
    }

    @DeleteMapping("/{ws_id}")
    ApiResponse<WorkScheduleEntity> deleteWorkSchedule(@PathVariable String ws_id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkScheduleEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Delete work schedule successfully")
                .data(workScheduleService.deleteWorkSchedule(ws_id, account))
                .build();
    }

    @GetMapping("/{ws_id}")
    ApiResponse<WorkScheduleEntity> getWorkSchedule(@PathVariable String ws_id) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkScheduleEntity>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get work schedule successfully")
                .data(workScheduleService.getWorkSchedule(ws_id,account))
                .build();
    }


}
