package com.pg.employee.dto.response;

import java.util.UUID;

public class WorkScheduleDTO {
    private UUID ws_id;
    private String ws_res_id;
    private String ws_note;
    private String ws_date;
    private LabelDTO label;
    private WorkingShiftDTO workingShift;
}
