package com.pg.employee.service;

import com.pg.employee.dto.request.workingShift.CreateWorkingShiftDto;
import com.pg.employee.dto.request.workingShift.UpdateWorkingShiftDto;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.WorkingShiftEntity;
import com.pg.employee.middleware.Account;

import java.util.List;

public interface WorkingShiftService {
    WorkingShiftEntity createWorkingShift(CreateWorkingShiftDto createWorkingShiftDto, Account account);
    WorkingShiftEntity getWorkingShift(String id);
    WorkingShiftEntity updateWorkingShift(UpdateWorkingShiftDto updateWorkingShiftDto, Account account);
    WorkingShiftEntity deleteWorkingShift(String id, Account account);
    WorkingShiftEntity restoreWorkingShift(String id, Account account);
    ResPagination<WorkingShiftEntity> getAllWorkingShift(int pageIndex , int pageSize, String wks_name, Account account);
    ResPagination<WorkingShiftEntity> getAllWorkingShiftRecycleBin(int pageIndex , int pageSize, String wks_name, Account account);
    List<WorkingShiftEntity> getAllWorkingShiftByRestaurantId(Account account);
}
