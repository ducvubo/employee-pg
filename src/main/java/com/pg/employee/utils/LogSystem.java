package com.pg.employee.utils;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LogSystem implements ILogSystem {
    private Object error; // Có thể là Exception hoặc bất kỳ object nào
    private String function;
    private String action;
    private Date time;
    private String message;
    private String className;
    private String type;

    @Override
    public Object getError() {
        return error;
    }

    @Override
    public String getFunction() {
        return function;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public Date getTime() {
        return time;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getType() {
        return type;
    }
}
