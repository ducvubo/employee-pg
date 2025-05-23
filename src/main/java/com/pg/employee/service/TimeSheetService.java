package com.pg.employee.service;

import com.pg.employee.dto.request.timeSheet.CreateTimeSheetDto;
import com.pg.employee.entities.TimeSheetEntity;
import com.pg.employee.middleware.Account;

public interface TimeSheetService {
    TimeSheetEntity createTimeSheet(CreateTimeSheetDto createTimeSheetDto, Account account);
}
