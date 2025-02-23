package com.pg.employee.dto.request.workingShift;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkingShiftDto {
    @NotBlank(message = "Tên ca không được để trống")
    private String wks_name;

    @NotBlank(message = "Mô tả không được để trống")
    private String wks_description;

    @NotBlank(message = "Thời gian bắt đầu không được để trống")
    private String wks_start_time;

    @NotBlank(message = "Thời gian kết thúc không được để trống")
    private String wks_end_time;
}
