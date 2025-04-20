package com.pg.employee.dto.request.workSchedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkScheduleDto {
    @NotBlank(message = "Ca làm việc không được để trống")
    private String wks_id;

    @NotBlank(message = "Nhãn không được để trống")
    private String lb_id;

    @NotBlank(message = "Ngày làm việc không được để trống")
    private String ws_date;

    @NotBlank(message = "Note không được để trống")
    private String ws_note;

    private List<String> listEmployeeId;
}

