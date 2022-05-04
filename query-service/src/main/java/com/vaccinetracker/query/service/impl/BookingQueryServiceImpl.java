package com.vaccinetracker.query.service.impl;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.query.client.service.BookingElasticQueryClient;
import com.vaccinetracker.query.model.BookingQueryRequest;
import com.vaccinetracker.query.model.BookingQueryResponse;
import com.vaccinetracker.query.service.BookingQueryService;
import com.vaccinetracker.query.transformer.IndexModelToResponseTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingQueryServiceImpl implements BookingQueryService {

    private final IndexModelToResponseTransformer indexModelToResponseTransformer;
    private final BookingElasticQueryClient<BookingIndexModel> bookingElasticQueryClient;

    @Override
    public BookingQueryResponse getBookingById(String id) {
        log.info("Querying document by id: {}", id);
        BookingIndexModel bookingIndexModel = bookingElasticQueryClient.getBookingById(id);
        return indexModelToResponseTransformer.getBookingResponseModel(bookingIndexModel);
    }

    @Override
    public List<BookingQueryResponse> getBookingByGovId(String govId) {
        log.info("Querying documents by govId: {}", govId);
        List<BookingIndexModel> bookingIndexModels =
                bookingElasticQueryClient.getBookingByGovId(govId);
        return indexModelToResponseTransformer.getBookingResponseModels(bookingIndexModels);
    }

    @Override
    public List<BookingQueryResponse> getBookingByGovIdAndStatus(String govId, String status) {
        log.info("Querying documents by govId: {} and status: {}", govId, status);
        List<BookingIndexModel> bookingIndexModels = bookingElasticQueryClient.getBookingByGovIdAndStatus(govId, status);
        return indexModelToResponseTransformer.getBookingResponseModels(bookingIndexModels);
    }

    @Override
    public List<BookingQueryResponse> getBookingByDateRange(BookingQueryRequest bookingQueryRequest) {
        String from = bookingQueryRequest.getFrom();
        String to = bookingQueryRequest.getTo();
        List<BookingIndexModel> bookingIndexModels =  Optional.ofNullable(bookingQueryRequest.getGovId())
                .map(govId -> {
                    log.info("Querying documents by govId: {} and date from: {} to: {}", govId, from, to);
                    return bookingElasticQueryClient.getByGovIdWithDateRange(govId, from, to);
                })
                .orElseGet(() -> {
                    String vaccineCenterId = bookingQueryRequest.getVaccineCenterId();
                    log.info("Querying documents by vaccineCenterId: {} and date from: {} to: {}",
                            vaccineCenterId, from, to);
                    return bookingElasticQueryClient.getByVaccineCenterIdWithDateRange(vaccineCenterId, from, to);
                });
        return indexModelToResponseTransformer.getBookingResponseModels(bookingIndexModels);
    }

    @Override
    public List<BookingQueryResponse> searchByText(String text) {
        log.info("Searching all documents by text: {}", text);
        List<BookingIndexModel> bookingIndexModels = bookingElasticQueryClient.searchBookingByText(text);
        return indexModelToResponseTransformer.getBookingResponseModels(bookingIndexModels);
    }
}
