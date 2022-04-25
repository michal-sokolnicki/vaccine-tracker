package com.vaccinetracker.elastic.query.client.service.impl;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.elastic.query.client.repository.BookingElasticsearchQueryRepository;
import com.vaccinetracker.elastic.query.client.service.BookingElasticQueryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Primary
@Service
public class BookingElasticQueryClientImpl extends ElasticRepositoryQueryClientBase<BookingIndexModel>
        implements BookingElasticQueryClient<BookingIndexModel> {

    private final BookingElasticsearchQueryRepository bookingElasticsearchQueryRepository;

    public BookingElasticQueryClientImpl(BookingElasticsearchQueryRepository bookingElasticsearchQueryRepository) {
        super(bookingElasticsearchQueryRepository);
        this.bookingElasticsearchQueryRepository = bookingElasticsearchQueryRepository;
    }

    @Override
    public BookingIndexModel getBookingById(String id) {
        return getById(id);
    }

    @Override
    public List<BookingIndexModel> getBookingByGovId(String govId) {
        List<BookingIndexModel> searchResults = bookingElasticsearchQueryRepository.findByGovId(govId);
        log.info("{} of document with govId {} retrieved successfully", searchResults.size(), govId);
        return searchResults;
    }

    @Override
    public List<BookingIndexModel> getBookingByGovIdAndStatus(String govId, String status) {
        List<BookingIndexModel> searchResults =
                bookingElasticsearchQueryRepository.findByGovIdAndStatus(govId, status);
        log.info("{} of document with govId {} and status {} retrieved successfully",
                searchResults.size(), govId, status);
        return searchResults;
    }

    @Override
    public List<BookingIndexModel> getByGovIdWithDateRange(String govId, String from, String to) {
        List<BookingIndexModel> searchResults =
                bookingElasticsearchQueryRepository.findByGovIdWithDateRange(govId, from, to);
        log.info("{} of document with govId {} and date from {} to {} retrieved successfully",
                searchResults.size(), govId, from, to);
        return searchResults;
    }

    @Override
    public List<BookingIndexModel> getByVaccineCenterIdWithDateRange(String vaccineCenterId, String from, String to) {
        List<BookingIndexModel> searchResults =
                bookingElasticsearchQueryRepository.findByVaccineCenterIdWithDateRange(vaccineCenterId, from, to);
        log.info("{} of document with vaccineCenterId {} and date from {} to {} retrieved successfully",
                searchResults.size(), vaccineCenterId, from, to);
        return searchResults;
    }

    @Override
    public List<BookingIndexModel> searchBookingByText(String text) {
        List<BookingIndexModel> searchResults =
                bookingElasticsearchQueryRepository.findByVaccineCenterOrVaccineType(text);
        log.info("{} of document with text {} retrieved successfully", searchResults.size(), text);
        return searchResults;
    }
}
