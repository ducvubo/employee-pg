package com.pg.employee.controller;

import com.pg.employee.dto.response.ApiResponse;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Test {

    @GetMapping("/no-authen")
    public ApiResponse<Account> testNoAuthen() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<Account>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Hello World No Authen")
                .data(account)
                .build();
    }

    @GetMapping("/authen")
    public ApiResponse<Account> testAuthen() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<Account>builder()
                .statusCode(10001)
                .message("Hello World Authen")
                .data(account)
                .build();
    }

    @GetMapping("/test-exception")
    public ApiResponse<Account> testException() {
        throw new BadRequestError("Test exception");
    }
}
