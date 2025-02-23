package com.pg.employee.dto.request.workingShift;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkingShiftDto extends CreateWorkingShiftDto {
    @NotBlank(message = "Id không được để trống")
    private String wks_id;
}
