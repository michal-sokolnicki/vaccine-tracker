package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.elastic.model.IndexModel;
import com.vaccinetracker.elastic.query.client.exception.ElasticQueryClientException;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import com.vaccinetracker.elastic.query.client.util.ElasticQueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class ElasticQueryClientBase<T extends IndexModel> {

    private final ElasticQueryUtil<T> elasticQueryUtil;
    private final ElasticsearchOperations elasticsearchOperations;
    private final Class<T> typeParameterClass;

    public ElasticQueryClientBase(ElasticQueryUtil<T> elasticQueryUtil, ElasticsearchOperations elasticsearchOperations,
                                  Class<T> typeParameterClass) {
        this.elasticQueryUtil = elasticQueryUtil;
        this.elasticsearchOperations = elasticsearchOperations;
        this.typeParameterClass = typeParameterClass;
    }

    public T getById(String id, String indexName) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<T> searchHit = elasticsearchOperations.searchOne(query,
                typeParameterClass, IndexCoordinates.of(indexName));
        return Optional.ofNullable(searchHit).map(indexModelSearchHit -> {
            log.info("Document with id {} retrieved successfully", indexModelSearchHit.getId());
            return indexModelSearchHit.getContent();
        }).orElseThrow(() -> {
            log.error("Document with id: {} not found", id);
            throw new ElasticQueryClientException(MessageFormat.format("Document with id: {} not found", id));
        });
    }

    public List<T> getByText(String text, String indexName, List<String> fields) {
        Query query = elasticQueryUtil.getSearchQueryByText(fields, text);
        return search(query, indexName, "{} of documents with text {} retrieved successfully", text);
    }

    protected List<T> search(Query query, String indexName, String logMessage, Object... logParams ) {
        SearchHits<T> searchResult = elasticsearchOperations.search(query, typeParameterClass,
                IndexCoordinates.of(indexName));
        log.info(logMessage, searchResult.getTotalHits(), logParams);
        return searchResult.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
