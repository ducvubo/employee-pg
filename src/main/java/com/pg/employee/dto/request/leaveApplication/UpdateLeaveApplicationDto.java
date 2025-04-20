package com.pg.employee.dto.request.leaveApplication;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLeaveApplicationDto extends CreateLeaveApplicationDto {
    @NotBlank(message = "Id không được để trống")
    private String leaveAppId;
}
