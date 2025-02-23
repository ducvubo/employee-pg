package com.pg.employee.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingShiftDTO {
    private UUID wks_id;
    private String wks_name;
    private String wks_start_time;
    private String wks_end_time;
    private String wks_description;
    private String wks_res_id;
}
