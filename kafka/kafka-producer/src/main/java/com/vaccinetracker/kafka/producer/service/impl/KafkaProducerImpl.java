package com.vaccinetracker.kafka.producer.service.impl;

import com.vaccinetracker.kafka.producer.service.KafkaProducer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;

@Slf4j
@Service
public class KafkaProducerImpl<T extends SpecificRecordBase> implements KafkaProducer<String, T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    protected KafkaProducerImpl(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, String key, T message) {
        log.info("Sending massage '{}' to topic '{}'", message, topicName);
        ListenableFuture<SendResult<String, T>> kafkaResultFuture =
                kafkaTemplate.send(topicName, key, message);
        addCallback(topicName, message, kafkaResultFuture);
    }

    private void addCallback(String topicName, T message,
                             ListenableFuture<SendResult<String, T>> kafkaResultFuture) {
        kafkaResultFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(@NonNull Throwable throwable) {
                log.error("Error while sending message '{}' to topic '{}'", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, T> result) {
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
