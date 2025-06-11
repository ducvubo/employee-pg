package com.pg.employee.service;

import com.pg.employee.dto.request.timeSheet.CreateTimeSheetDto;
import com.pg.employee.entities.TimeSheetEntity;
import com.pg.employee.entities.WorkScheduleEntity;
import com.pg.employee.middleware.Account;

import java.util.List;

public interface TimeSheetService {
    WorkScheduleEntity createTimeSheet(CreateTimeSheetDto createTimeSheetDto, Account account);
    List<TimeSheetEntity> getTimeSheetsByWorkScheduleId(String wsId, Account account);
}
