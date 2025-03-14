package com.pg.employee.utils;

import java.util.Date;

public interface ILogSystem {
    Object getError(); // Có thể là null
    String getFunction();
    String getAction();
    Date getTime();
    String getMessage();
    String getClassName();
    String getType(); // "infor", "success", "error", "warning"
}