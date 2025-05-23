package com.pg.employee.dto.request.timeSheet;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTimeSheetDto {
    @NotBlank(message = "Tên ca không được để trống")
    private String tsEmployeeId;

    @NotBlank(message = "Thời gian checkin không được để trống")
    private String tsCheckIn;
}
