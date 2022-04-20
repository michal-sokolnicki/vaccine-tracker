package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.config.ElasticQueryConfigData;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.query.client.exception.ElasticQueryClientException;
import com.vaccinetracker.elastic.query.client.repository.BookingElasticsearchQueryRepository;
import com.vaccinetracker.elastic.query.client.service.ElasticQueryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Slf4j
@Primary
@Service
public class BookingElasticRepositoryQueryClient implements ElasticQueryClient<BookingIndexModel> {

    private final BookingElasticsearchQueryRepository bookingElasticsearchQueryRepository;

    public BookingElasticRepositoryQueryClient(BookingElasticsearchQueryRepository bookingElasticsearchQueryRepository) {
        this.bookingElasticsearchQueryRepository = bookingElasticsearchQueryRepository;
    }

    @Override
    public BookingIndexModel getIndexModelById(String id) {
        return bookingElasticsearchQueryRepository.findById(id)
                .map(bookingIndexModel -> {
                    log.info("Document with id {} retrieved successfully", id);
                    return bookingIndexModel;
                })
                .orElseThrow(() -> new ElasticQueryClientException(
                        MessageFormat.format("Document with id: {} not found", id)));
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByField(String govId) {
        List<BookingIndexModel> searchResults = bookingElasticsearchQueryRepository.findByGovId(govId);
        log.info("{} of document with govId {} retrieved successfully", searchResults.size(), govId);
        return searchResults;
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByParams(String... params) {
        String govId = params[0];
        String status = params[1];
        List<BookingIndexModel> searchResults =
                bookingElasticsearchQueryRepository.findByGovIdAndStatus(govId, status);
        log.info("{} of document with govId {} and status {} retrieved successfully",
                searchResults.size(), govId, status);
        return searchResults;
    }

    @Override
    public List<BookingIndexModel> getIndexModelsByText(String text) {
        List<BookingIndexModel> searchResults =
                bookingElasticsearchQueryRepository.findByVaccineCenterOrVaccineType(text);
        log.info("{} of document with text {} retrieved successfully", searchResults.size(), text);
        return searchResults;
    }
}