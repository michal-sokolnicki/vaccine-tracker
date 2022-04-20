package com.vaccinetracker.query.service.impl;

import com.vaccinetracker.config.QueryServiceConfigData;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import com.vaccinetracker.query.model.QueryServiceResponseModel;
import com.vaccinetracker.query.service.QueryService;
import com.vaccinetracker.query.transformer.IndexModelToResponseModelTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BookingQueryService implements QueryService {

    private final IndexModelToResponseModelTransformer indexModelToResponseModelTransformer;
    private final ElasticQueryClient<BookingIndexModel> elasticQueryClient;

    public BookingQueryService(IndexModelToResponseModelTransformer indexModelToResponseModelTransformer,
                               ElasticQueryClient<BookingIndexModel> elasticQueryClient) {
        this.indexModelToResponseModelTransformer = indexModelToResponseModelTransformer;
        this.elasticQueryClient = elasticQueryClient;
    }

    @Override
    public QueryServiceResponseModel getBookingById(String id) {
        log.info("Querying document by id: {}", id);
        BookingIndexModel bookingIndexModel = elasticQueryClient.getIndexModelById(id);
        return indexModelToResponseModelTransformer.getResponseModel(bookingIndexModel);
    }

    @Override
    public List<QueryServiceResponseModel> getBookingHistoryByGovId(String govId) {
        log.info("Querying documents by govId: {}", govId);
        List<BookingIndexModel> bookingIndexModels =
                elasticQueryClient.getIndexModelsByField(govId);
        return indexModelToResponseModelTransformer.getResponseModels(bookingIndexModels);
    }

    @Override
    public List<QueryServiceResponseModel> getBookingByGovIdAndStatus(String govId, String status) {
        log.info("Querying documents by govId: {} and status: {}", govId, status);
        List<BookingIndexModel> bookingIndexModels = elasticQueryClient.getIndexModelsByParams(govId, status);
        return indexModelToResponseModelTransformer.getResponseModels(bookingIndexModels);
    }

    @Override
    public List<QueryServiceResponseModel> searchByText(String text) {
        log.info("Searching all documents by text: {}", text);
        List<BookingIndexModel> bookingIndexModels = elasticQueryClient.getIndexModelsByText(text);
        return indexModelToResponseModelTransformer.getResponseModels(bookingIndexModels);
    }
}
