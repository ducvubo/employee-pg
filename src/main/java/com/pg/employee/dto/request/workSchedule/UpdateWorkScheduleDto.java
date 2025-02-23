package com.pg.employee.dto.request.workSchedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkScheduleDto extends CreateWorkScheduleDto {
    @NotBlank(message = "ID lịch làm việc không được để trống")
    private String ws_id;
}
