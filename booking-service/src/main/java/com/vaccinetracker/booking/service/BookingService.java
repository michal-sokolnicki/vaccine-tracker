package com.vaccinetracker.booking.service;

import com.vaccinetracker.booking.entity.BookingRequest;
import com.vaccinetracker.booking.service.transformer.BookingToAvroTransformer;
import com.vaccinetracker.config.KafkaConfigData;
import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookingService {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, BookingAvroModel> kafkaProducer;
    private final BookingToAvroTransformer bookingToAvroTransformer;

    public BookingService(KafkaConfigData kafkaConfigData, KafkaProducer<Long, BookingAvroModel> kafkaProducer,
                          BookingToAvroTransformer bookingToAvroTransformer) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.bookingToAvroTransformer = bookingToAvroTransformer;
    }

    public void book(BookingRequest bookingRequest) {
        BookingAvroModel bookingAvroModel = bookingToAvroTransformer.getBookingAvroModel(bookingRequest);
        kafkaProducer.send(kafkaConfigData.getTopicName(), bookingAvroModel.getUserId(), bookingAvroModel);
    }
}
