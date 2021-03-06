package com.vaccinetracker.elastic.config;

import com.vaccinetracker.config.ElasticConfigData;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.vaccinetracker.elastic")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;

    public ElasticSearchConfig(ElasticConfigData elasticConfigData) {
        this.elasticConfigData = elasticConfigData;
    }

    @Bean
    @Override
    @NonNull
    public RestHighLevelClient elasticsearchClient() {
        UriComponents serverUri = UriComponentsBuilder.fromHttpUrl(elasticConfigData.getConnectionUrl()).build();
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(
                                Objects.requireNonNull(serverUri.getHost()),
                                serverUri.getPort(),
                                serverUri.getScheme()))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                                        .setConnectTimeout(elasticConfigData.getConnectionTimeoutMs())
                                        .setSocketTimeout(elasticConfigData.getSocketTimeoutMs())
                )
        );
    }

    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
    public ElasticsearchOperations elasticsearchRestTemplate(){
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
