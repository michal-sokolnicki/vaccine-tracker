package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.service.BookingService;
import com.vaccinetracker.booking.service.QueryWebClient;
import com.vaccinetracker.booking.service.transformer.BookingToIndexModelTransformer;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingToIndexModelTransformer bookingToIndexModelTransformer;
    private final ElasticIndexClient<BookingIndexModel> elasticIndexClient;
    private final QueryWebClient queryWebClient;

    public BookingServiceImpl(BookingToIndexModelTransformer bookingToIndexModelTransformer,
                              ElasticIndexClient<BookingIndexModel> elasticIndexClient, QueryWebClient queryWebClient) {
        this.bookingToIndexModelTransformer = bookingToIndexModelTransformer;
        this.elasticIndexClient = elasticIndexClient;
        this.queryWebClient = queryWebClient;
    }

    @Override
    public void booking(BookingRequest bookingRequest) {
        BookingIndexModel bookingIndexModelToCreate = bookingToIndexModelTransformer.getBookingIndexModelToCreate(bookingRequest);
        String id = elasticIndexClient.save(bookingIndexModelToCreate);
        log.info("Document with id: {} has been created in elasticsearch", id);
    }

    @Override
    public void updateBooking(String id, BookingRequest bookingRequest) {
        BookingIndexModel bookingIndexModelToUpdate =
                bookingToIndexModelTransformer.getBookingIndexModelToUpdate(id, bookingRequest);
        elasticIndexClient.save(bookingIndexModelToUpdate);
        log.info("Document with id: {} has been updated in elasticsearch", id);
    }
}
