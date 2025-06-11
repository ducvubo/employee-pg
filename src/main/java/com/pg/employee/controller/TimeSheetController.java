package com.pg.employee.controller;

import com.pg.employee.dto.request.timeSheet.CreateTimeSheetDto;
import com.pg.employee.dto.request.workSchedule.CreateWorkScheduleDto;
import com.pg.employee.dto.response.ApiResponse;
import com.pg.employee.entities.TimeSheetEntity;
import com.pg.employee.entities.WorkScheduleEntity;
import com.pg.employee.middleware.Account;
import com.pg.employee.service.TimeSheetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-sheet")
@RequiredArgsConstructor
public class TimeSheetController {

    @Autowired
    private TimeSheetService timeSheetService;

    @PostMapping
    ApiResponse<WorkScheduleEntity> createTimeSheet(@Valid @RequestBody CreateTimeSheetDto createTimeSheetDto) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<WorkScheduleEntity>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Create time sheet successfully")
                .data(timeSheetService.createTimeSheet(createTimeSheetDto, account))
                .build();
    }

    @GetMapping("/get-by-work-schedule/{workScheduleId}")
    public ApiResponse<List<TimeSheetEntity>> getByWorkSchedule(
            @PathVariable("workScheduleId") String workScheduleId) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<TimeSheetEntity> data = timeSheetService.getTimeSheetsByWorkScheduleId(workScheduleId, account);
        return ApiResponse.<List<TimeSheetEntity>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get timesheets by work schedule ID successfully")
                .data(data)
                .build();
    }
}
