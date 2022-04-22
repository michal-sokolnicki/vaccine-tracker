package com.vaccinetracker.kafka.producer.service.impl;

import com.vaccinetracker.kafka.avro.model.BookingAvroModel;
import com.vaccinetracker.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;

@Slf4j
@Service
public class BookingKafkaProducer implements KafkaProducer<String, BookingAvroModel> {

    private final KafkaTemplate<String, BookingAvroModel> kafkaTemplate;

    public BookingKafkaProducer(KafkaTemplate<String, BookingAvroModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, String key, BookingAvroModel message) {
        log.info("Sending massage '{}' to topic '{}'", message, topicName);
        ListenableFuture<SendResult<String, BookingAvroModel>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, message);
        addCallback(topicName, message, kafkaResultFuture);

    }

    private void addCallback(String topicName, BookingAvroModel message,
                             ListenableFuture<SendResult<String, BookingAvroModel>> kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<SendResult<String, BookingAvroModel>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message '{}' to topic '{}'", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, BookingAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.debug("Received new metadata. Topic: {}, Partition: {}, Offset: {}, Timestamp: {}," +
                                "at time: {}", metadata.topic(), metadata.partition(), metadata.offset(),
                        metadata.timestamp(), System.nanoTime());
            }
        });
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer");
            kafkaTemplate.destroy();
        }
    }
}
