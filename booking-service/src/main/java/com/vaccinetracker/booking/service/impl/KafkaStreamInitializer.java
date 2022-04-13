package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.service.StreamInitializer;
import com.vaccinetracker.config.KafkaConfigData;
import com.vaccinetracker.kafka.admin.client.KafkaAdminClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaStreamInitializer implements StreamInitializer {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializer(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
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
