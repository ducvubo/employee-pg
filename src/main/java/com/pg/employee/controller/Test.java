package com.pg.employee.controller;

// import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.pg.employee.dto.response.ApiResponse;
import com.pg.employee.exception.BadRequestError;
import com.pg.employee.middleware.Account;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Test {

    // private final ElasticsearchClient elasticsearchClient;

    @GetMapping("/no-authen")
    public ApiResponse<Account> testNoAuthen() throws IOException {
        // Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        elasticsearchClient.indices().create(c -> c.index("testindex"));
//
//        // Thêm dữ liệu vào index
//        IndexRequest<Map<String, Object>> request = IndexRequest.of(i -> i
//                .index("testindex")
//                .id("id")
//                .document(Map.of("name", "Duc", "age", 20))
//        );
//
//        IndexResponse response = elasticsearchClient.index(request);
//        return ApiResponse.<Account>builder()
//                .statusCode(10001)
//                .message("Hello World No Authen")
//                .data(account)
//                .build();
        throw new BadRequestError("Test exception");
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
