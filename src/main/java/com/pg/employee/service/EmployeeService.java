package com.pg.employee.service;

import com.pg.employee.grpc.api.Api;

public interface EmployeeService {
    Api.IBackendGRPC findOneEmployeeById(String id, String eplResId);
}
