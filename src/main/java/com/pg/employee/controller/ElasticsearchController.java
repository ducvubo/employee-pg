package com.pg.employee.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.pg.employee.dto.response.ApiResponse;
import com.pg.employee.entities.LabelEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RequestMapping("/elasticsearch")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ElasticsearchController {

    private final ElasticsearchClient elasticsearchClient;

    @PostMapping("/create-index-and-add-data")
    public ApiResponse<String> createIndexAndAddData(@RequestBody Map<String, Object> data) throws IOException {
            elasticsearchClient.indices().create(c -> c.index("testindex"));

            // Thêm dữ liệu vào index
            IndexRequest<Map<String, Object>> request = IndexRequest.of(i -> i
                    .index("testindex")
                    .id("id")
                    .document(data)
            );

            IndexResponse response = elasticsearchClient.index(request);
            return ApiResponse.<String>builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Update status label successfully")
                    .data("labelService.updateStatus(updateStatusLabelDto, account)")
                    .build();

    }
}
