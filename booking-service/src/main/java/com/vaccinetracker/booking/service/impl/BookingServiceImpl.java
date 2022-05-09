package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.model.BookingRequest;
import com.vaccinetracker.booking.service.BookingService;
import com.vaccinetracker.booking.service.KafkaService;
import com.vaccinetracker.booking.service.transformer.BookingToIndexModelTransformer;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.kafka.avro.model.VaccinationStatusAvroModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingToIndexModelTransformer bookingToIndexModelTransformer;
    private final ElasticIndexClient<BookingIndexModel> elasticIndexClient;
    private final KafkaService kafkaService;

    @Override
    public void booking(BookingRequest bookingRequest) {
        BookingIndexModel bookingIndexModelToCreate = bookingToIndexModelTransformer.getBookingIndexModelToCreate(bookingRequest);
        String id = elasticIndexClient.save(bookingIndexModelToCreate);
        log.info("Document with id: {} has been created in elasticsearch", id);
        kafkaService.publishBooking(id, bookingRequest);
    }

    @Override
    public void updateBooking(String id, BookingRequest bookingRequest) {
        BookingIndexModel bookingIndexModelToUpdate =
                bookingToIndexModelTransformer.getBookingIndexModelToUpdate(id, bookingRequest);
        elasticIndexClient.save(bookingIndexModelToUpdate);
        log.info("Document with id: {} has been updated in elasticsearch", id);
    }

    @Override
    public void processMessages(List<VaccinationStatusAvroModel> messages) {
        kafkaService.processMessages(messages);
    }
}
