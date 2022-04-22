package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.service.BookingService;
import com.vaccinetracker.booking.service.transformer.BookingToAvroTransformer;
import com.vaccinetracker.booking.service.transformer.BookingToIndexModelTransformer;
import com.vaccinetracker.config.KafkaConfigData;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingToIndexModelTransformer bookingToIndexModelTransformer;
    private final ElasticIndexClient<BookingIndexModel> elasticIndexClient;
    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<String, BookingAvroModel> kafkaProducer;
    private final BookingToAvroTransformer bookingToAvroTransformer;

    public BookingServiceImpl(BookingToIndexModelTransformer bookingToIndexModelTransformer,
                              ElasticIndexClient<BookingIndexModel> elasticIndexClient,
                              KafkaConfigData kafkaConfigData, KafkaProducer<String, BookingAvroModel> kafkaProducer,
                              BookingToAvroTransformer bookingToAvroTransformer) {
        this.bookingToIndexModelTransformer = bookingToIndexModelTransformer;
        this.elasticIndexClient = elasticIndexClient;
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.bookingToAvroTransformer = bookingToAvroTransformer;
    }

    @Override
    public void booking(BookingRequest bookingRequest) {
        BookingIndexModel bookingIndexModelToCreate = bookingToIndexModelTransformer.getBookingIndexModelToCreate(bookingRequest);
        String id = elasticIndexClient.save(bookingIndexModelToCreate);
        log.info("Document with id: {} has been created in elasticsearch", id);
        BookingAvroModel bookingAvroModel = bookingToAvroTransformer.getBookingAvroModel(bookingRequest);
        kafkaProducer.send(kafkaConfigData.getTopicName(), id, bookingAvroModel);
    }

    @Override
    public void updateBooking(String id, BookingRequest bookingRequest) {
        BookingIndexModel bookingIndexModelToUpdate =
                bookingToIndexModelTransformer.getBookingIndexModelToUpdate(id, bookingRequest);
        elasticIndexClient.save(bookingIndexModelToUpdate);
        log.info("Document with id: {} has been updated in elasticsearch", id);
    }
}
