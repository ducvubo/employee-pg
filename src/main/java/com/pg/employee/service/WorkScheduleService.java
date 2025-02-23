package com.pg.employee.service;

import com.pg.employee.dto.request.workSchedule.CreateWorkScheduleDto;
import com.pg.employee.dto.request.workSchedule.UpdateWorkScheduleDto;
import com.pg.employee.entities.WorkScheduleEntity;
import com.pg.employee.middleware.Account;

import java.util.Date;
import java.util.List;

public interface WorkScheduleService {

    WorkScheduleEntity createWorkSchedule(CreateWorkScheduleDto createWorkScheduleDto, Account account);
    WorkScheduleEntity getWorkSchedule(String ws_id,Account account);
    WorkScheduleEntity updateWorkSchedule(UpdateWorkScheduleDto updateWorkScheduleDto,Account account);
    WorkScheduleEntity deleteWorkSchedule(String ws_id,Account account);
    WorkScheduleEntity restoreWorkSchedule(String ws_id,Account account);
    List<WorkScheduleEntity> getListWorkSchedule(String start_date, String end_date,Account account);
}
