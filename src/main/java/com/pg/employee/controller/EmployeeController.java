package com.pg.employee.controller;

import com.pg.employee.grpcjava.api.Api;
import com.pg.employee.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public Api.IBackendGRPC getEmployee(@RequestParam String id, @RequestParam String eplResId) {
        return employeeService.findOneEmployeeById(id, eplResId);
    }
}
