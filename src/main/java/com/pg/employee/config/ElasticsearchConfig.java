package com.pg.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("elasticsearch.taphoaictu.id.vn:443") // Địa chỉ Elasticsearch
                .usingSsl() // Sử dụng SSL
                .withBasicAuth("elastic", "Duc17052003*") // Xác thực
                .build();
    }
}
