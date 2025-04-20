package com.pg.employee.dto.request.leaveApplication;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLeaveApplicationDto {
    @NotBlank(message = "Loại đơn không được để trống")
    private String leaveType;

    @NotBlank(message = "Ngày bắt đầu không được để trống")
    private String startDate;

    @NotBlank(message = "Ngày kết thúc không được để trống")
    private String endDate;

    @NotBlank(message = "Lý do không được để trống")
    private String reason;
}
