package com.vaccinetracker.booking.service.impl;

import com.vaccinetracker.booking.service.Initializer;
import com.vaccinetracker.config.KafkaConfigData;
import com.vaccinetracker.kafka.admin.client.KafkaAdminClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaInitializer implements Initializer {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics with name '{}' are ready for operations", kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}
