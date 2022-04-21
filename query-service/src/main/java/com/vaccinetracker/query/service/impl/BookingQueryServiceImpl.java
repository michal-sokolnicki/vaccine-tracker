package com.vaccinetracker.query.service.impl;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import com.vaccinetracker.query.model.BookingQueryServiceResponseModel;
import com.vaccinetracker.query.transformer.IndexModelToResponseModelTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookingQueryServiceImpl implements com.vaccinetracker.query.service.BookingQueryService {

    private final IndexModelToResponseModelTransformer indexModelToResponseModelTransformer;
    private final ElasticQueryClient<BookingIndexModel> elasticQueryClient;

    public BookingQueryServiceImpl(IndexModelToResponseModelTransformer indexModelToResponseModelTransformer,
                                   ElasticQueryClient<BookingIndexModel> elasticQueryClient) {
        this.indexModelToResponseModelTransformer = indexModelToResponseModelTransformer;
        this.elasticQueryClient = elasticQueryClient;
    }

    @Override
    public BookingQueryServiceResponseModel getBookingById(String id) {
        log.info("Querying document by id: {}", id);
        BookingIndexModel bookingIndexModel = elasticQueryClient.getIndexModelById(id);
        return indexModelToResponseModelTransformer.getBookingResponseModel(bookingIndexModel);
    }

    @Override
    public List<BookingQueryServiceResponseModel> getBookingHistoryByGovId(String govId) {
        log.info("Querying documents by govId: {}", govId);
        List<BookingIndexModel> bookingIndexModels =
                elasticQueryClient.getIndexModelsByField(govId);
        return indexModelToResponseModelTransformer.getBookingResponseModels(bookingIndexModels);
    }

    @Override
    public List<BookingQueryServiceResponseModel> getBookingByGovIdAndStatus(String govId, String status) {
        log.info("Querying documents by govId: {} and status: {}", govId, status);
        List<BookingIndexModel> bookingIndexModels = elasticQueryClient.getIndexModelsByParams(govId, status);
        return indexModelToResponseModelTransformer.getBookingResponseModels(bookingIndexModels);
    }

    @Override
    public List<BookingQueryServiceResponseModel> searchByText(String text) {
        log.info("Searching all documents by text: {}", text);
        List<BookingIndexModel> bookingIndexModels = elasticQueryClient.getIndexModelsByText(text);
        return indexModelToResponseModelTransformer.getBookingResponseModels(bookingIndexModels);
    }
}
