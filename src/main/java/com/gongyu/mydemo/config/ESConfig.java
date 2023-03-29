package com.gongyu.mydemo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import javax.annotation.PostConstruct;

//@Configuration
public class ESConfig extends AbstractElasticsearchConfiguration {


    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("192.168.96.96:31749")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }


//    @PostConstruct
//    void init() {
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
//    }

}
