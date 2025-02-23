package com.pg.employee.service;

import com.pg.employee.dto.request.label.CreateLabelDto;
import com.pg.employee.dto.request.label.UpdateLabelDto;
import com.pg.employee.dto.request.label.UpdateStatusLabelDto;
import com.pg.employee.dto.response.ResPagination;
import com.pg.employee.entities.LabelEntity;
import com.pg.employee.middleware.Account;

import java.util.List;
import java.util.UUID;

public interface LabelService {
    LabelEntity createLabel(CreateLabelDto createLabelDto, Account account);
    LabelEntity getLabel(String id);
    LabelEntity updateLabel(UpdateLabelDto updateLabelDto, Account account);
    LabelEntity deleteLabel(String id, Account account);
    LabelEntity restoreLabel(String id, Account account);
    LabelEntity updateStatus(UpdateStatusLabelDto updateStatusLabelDto, Account account);
    ResPagination<LabelEntity> getAllLabel(int pageIndex , int pageSize, String lb_name, Account account);
    ResPagination<LabelEntity> getAllLabelRecycleBin(int pageIndex , int pageSize, String lb_name, Account account);
    List<LabelEntity> getAllLabelByRestaurantId(Account account);
}
