package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.entity.BookingRequestUpdate;
import com.vaccinetracker.booking.service.BookingService;
import com.vaccinetracker.booking.service.transformer.BookingToIndexModelTransformer;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    /*private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, BookingAvroModel> kafkaProducer;
    private final BookingToAvroTransformer bookingToAvroTransformer;*/

    /*public BookingService(KafkaConfigData kafkaConfigData, KafkaProducer<Long, BookingAvroModel> kafkaProducer,
                          BookingToAvroTransformer bookingToAvroTransformer) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.bookingToAvroTransformer = bookingToAvroTransformer;
    }*/

    private final BookingToIndexModelTransformer bookingToIndexModelTransformer;
    private final ElasticIndexClient<BookingIndexModel> elasticIndexClient;

    public BookingServiceImpl(BookingToIndexModelTransformer bookingToIndexModelTransformer,
                              ElasticIndexClient<BookingIndexModel> elasticIndexClient) {
        this.bookingToIndexModelTransformer = bookingToIndexModelTransformer;
        this.elasticIndexClient = elasticIndexClient;
    }

    @Override
    public void booking(BookingRequest bookingRequest) {
        /*BookingAvroModel bookingAvroModel = bookingToAvroTransformer.getBookingAvroModel(bookingRequest);
        kafkaProducer.send(kafkaConfigData.getTopicName(), bookingAvroModel.getUserId(), bookingAvroModel);*/
        BookingIndexModel bookingIndexModelToCreate = bookingToIndexModelTransformer.getBookingIndexModelToCreate(bookingRequest);
        String id = elasticIndexClient.save(bookingIndexModelToCreate);
        log.info("Document with id: {} has been created in elasticsearch", id);
    }

    @Override
    public void updateBooking(String id, BookingRequestUpdate bookingRequestUpdate) {
            BookingIndexModel bookingIndexModel = elasticIndexClient.findById(id);
        BookingIndexModel bookingIndexModelToUpdate =
                bookingToIndexModelTransformer.getBookingIndexModelToUpdate(bookingIndexModel, bookingRequestUpdate);
        elasticIndexClient.save(bookingIndexModelToUpdate);
        log.info("Document with id: {} has been updated in elasticsearch", id);
    }
}
