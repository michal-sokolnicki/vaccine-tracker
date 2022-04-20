package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.config.ElasticConfigData;
import com.vaccinetracker.config.ElasticQueryConfigData;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.query.client.exception.ElasticQueryClientException;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import com.vaccinetracker.elastic.query.client.util.ElasticQueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingElasticQueryClient implements ElasticQueryClient<BookingIndexModel> {

    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryUtil<BookingIndexModel> elasticQueryUtil;
    private final ElasticQueryConfigData elasticQueryConfigData;
    private final ElasticsearchOperations elasticsearchOperations;

    public BookingElasticQueryClient(ElasticConfigData elasticConfigData, ElasticQueryUtil<BookingIndexModel> elasticQueryUtil,
                                     ElasticQueryConfigData elasticQueryConfigData, ElasticsearchOperations elasticsearchOperations) {
        this.elasticConfigData = elasticConfigData;
        this.elasticQueryUtil = elasticQueryUtil;
        this.elasticQueryConfigData = elasticQueryConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public BookingIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<BookingIndexModel> searchHit = elasticsearchOperations.searchOne(query, BookingIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        return Optional.ofNullable(searchHit).map(bookingIndexModelSearchHit -> {
            log.info("Document with id {} retrieved successfully", bookingIndexModelSearchHit.getId());
            return bookingIndexModelSearchHit.getContent();
        }).orElseThrow(() -> {
            log.error("Document with id: {} not found", id);
            throw new ElasticQueryClientException(MessageFormat.format("Document with id: {} not found", id));
        });
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByField(String govId) {
        Query query = elasticQueryUtil.getSearchQueryByParams(Map.of(elasticQueryConfigData.getGovIdField(), govId));
        return search(query, "{} of documents with govId {} retrieved successfully", govId);
    }

    private List<BookingIndexModel> search(Query query, String logMessage, Object... logParams ) {
        SearchHits<BookingIndexModel> searchResult = elasticsearchOperations.search(query, BookingIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        log.info(logMessage, searchResult.getTotalHits(), logParams);
        return searchResult.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByParams(String... params) {
        String govId = params[0];
        String status = params[1];
        Query query = elasticQueryUtil.getSearchQueryByParams(Map.of(elasticQueryConfigData.getGovIdField(), govId,
                elasticQueryConfigData.getStatusField(), status));
        return search(query,
                "{} of documents with govId {} and status {} retrieved successfully", govId, status);
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByText(elasticQueryConfigData.getFields(), text);
        return search(query, "{} of documents with text {} retrieved successfully", text);
    }
}
