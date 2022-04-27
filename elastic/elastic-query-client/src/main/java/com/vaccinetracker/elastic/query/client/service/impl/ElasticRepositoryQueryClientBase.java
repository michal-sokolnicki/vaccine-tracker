package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.elastic.model.IndexModel;
import com.vaccinetracker.elastic.query.client.exception.ElasticQueryClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.text.MessageFormat;

@Slf4j
public abstract class ElasticRepositoryQueryClientBase<T extends IndexModel> {

    private final ElasticsearchRepository<T, String> elasticsearchRepository;

    protected ElasticRepositoryQueryClientBase(ElasticsearchRepository<T, String> elasticsearchRepository) {
        this.elasticsearchRepository = elasticsearchRepository;
    }

    public T getById(String id) {
        return elasticsearchRepository.findById(id)
                .map(indexModel -> {
                    log.info("Document with id {} retrieved successfully", id);
                    return indexModel;
                })
                .orElseThrow(() -> new ElasticQueryClientException(
                        MessageFormat.format("Document with id: {} not found", id)));
    }
}
