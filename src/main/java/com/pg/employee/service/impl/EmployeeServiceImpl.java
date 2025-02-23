package com.pg.employee.service.impl;

import com.pg.employee.grpcjava.EmployeeProto.Employee;
import com.pg.employee.grpcjava.api.Api;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.employee.config.GrpcClient;
import com.pg.employee.service.EmployeeService;
import com.pg.employee.models.EmployeeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class EmployeeServiceImpl implements EmployeeService {

    private final GrpcClient grpcClient;

    // Constructor injection
    public EmployeeServiceImpl(GrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    @Override
    public Api.IBackendGRPC findOneEmployeeById(String id, String eplResId) {
        try {
            // Gửi request gRPC
            Employee.ReqFindOneEmployById request = Employee.ReqFindOneEmployById.newBuilder()
                    .setId(id)
                    .setEplResId(eplResId)
                    .build();

            Api.IBackendGRPC employee = grpcClient.getBlockingStub().findOneEmployeeById(request);

            ObjectMapper objectMapper = new ObjectMapper();
            Boolean data = employee.getStatus();
            EmployeeModel employeeData = objectMapper.readValue(employee.getData(), EmployeeModel.class);
            System.out.println(employeeData.get_id());
            return employee;
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để debug
            return null;
        }
    }

    public void shutdown() throws InterruptedException {
        grpcClient.shutdown();
    }
}
