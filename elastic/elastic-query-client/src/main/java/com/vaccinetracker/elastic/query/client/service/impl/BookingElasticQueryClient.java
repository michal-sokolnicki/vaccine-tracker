package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.config.ElasticConfigData;
import com.vaccinetracker.config.ElasticQueryConfigData;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import com.vaccinetracker.elastic.query.client.util.ElasticQueryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BookingElasticQueryClient extends ElasticQueryClientBase<BookingIndexModel>
        implements ElasticQueryClient<BookingIndexModel> {

    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryUtil<BookingIndexModel> elasticQueryUtil;
    private final ElasticQueryConfigData elasticQueryConfigData;

    public BookingElasticQueryClient(ElasticConfigData elasticConfigData, ElasticQueryUtil<BookingIndexModel> elasticQueryUtil,
                                     ElasticQueryConfigData elasticQueryConfigData, ElasticsearchOperations elasticsearchOperations) {
        super(elasticQueryUtil, elasticsearchOperations, BookingIndexModel.class);
        this.elasticConfigData = elasticConfigData;
        this.elasticQueryUtil = elasticQueryUtil;
        this.elasticQueryConfigData = elasticQueryConfigData;
    }

    @Override
    public BookingIndexModel getIndexModelById(String id) {
        return getById(id, elasticConfigData.getBookingIndexName());
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByField(String govId) {
        Query query = elasticQueryUtil.getSearchQueryByParams(Map.of(elasticQueryConfigData.getGovIdField(), govId));
        return search(query, elasticConfigData.getBookingIndexName(),
                "{} of documents with govId {} retrieved successfully", govId);
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByParams(String... params) {
        String govId = params[0];
        String status = params[1];
        Query query = elasticQueryUtil.getSearchQueryByParams(Map.of(elasticQueryConfigData.getGovIdField(), govId,
                elasticQueryConfigData.getStatusField(), status));
        return search(query, elasticConfigData.getBookingIndexName(),
                "{} of documents with govId {} and status {} retrieved successfully", govId, status);
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByText(String text) {
        return getByText(text, elasticConfigData.getBookingIndexName(),
                elasticQueryConfigData.getBookingFields());
    }
}
