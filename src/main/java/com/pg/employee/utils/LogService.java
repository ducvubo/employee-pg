package com.pg.employee.utils;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LogService {

    private static final Logger logger = LoggerFactory.getLogger(LogService.class);
    private static final String INDEX_SYSTEM_LOG = "system-log-inventory-pg";

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveLogSystem(ILogSystem logData) {
        try {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("error", logData.getError() != null ? objectMapper.writeValueAsString(logData.getError()) : null);
            logMap.put("function", logData.getFunction());
            logMap.put("action", logData.getAction());
            logMap.put("time", formatDate(logData.getTime()));
            logMap.put("message", logData.getMessage());
            logMap.put("class", logData.getClassName());
            logMap.put("type", logData.getType());

            IndexRequest<Map<String, Object>> indexRequest = new IndexRequest.Builder<Map<String, Object>>()
                    .index(INDEX_SYSTEM_LOG)
                    .id(UUID.randomUUID().toString())
                    .document(logMap)
                    .build();

            elasticsearchClient.index(indexRequest);
        } catch (Exception e) {
            logger.error("Failed to save system log to Elasticsearch", e);
            throw new RuntimeException("Error saving log: " + e.getMessage());
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        return sdf.format(date);
    }
}
