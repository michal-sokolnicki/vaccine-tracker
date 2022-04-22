package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.service.Initializer;
import com.vaccinetracker.config.KafkaConfigData;
import com.vaccinetracker.kafka.admin.client.KafkaAdminClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaInitializer implements Initializer {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    public KafkaInitializer(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics with name '{}' are ready for operations", kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}
