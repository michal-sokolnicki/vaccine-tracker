package com.vaccinetracker.kafka.admin.client;

import com.vaccinetracker.config.KafkaConfigData;
import com.vaccinetracker.config.RetryConfigData;
import com.vaccinetracker.kafka.admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class KafkaAdminClient {

    private final KafkaConfigData kafkaConfigData;
    private final RetryConfigData retryConfigData;
    private final AdminClient adminClient;
    private final RetryTemplate retryTemplate;
    private final WebClient webClient;

    public KafkaAdminClient(KafkaConfigData kafkaConfigData, RetryConfigData retryConfigData,
                            AdminClient adminClient, RetryTemplate retryTemplate, WebClient webClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.retryConfigData = retryConfigData;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
        this.webClient = webClient;
    }

    public void createTopics() {
        CreateTopicsResult createTopicsResult;
        try {
            createTopicsResult = retryTemplate.execute(this::createKafkaTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry creating kafka topic(s)!", t);
        }
        checkTopicsCreated();
    }

    private CreateTopicsResult createKafkaTopics(RetryContext retryContext) {
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
        List<NewTopic> kafkaTopics = topicNames.stream()
                .map(topic -> new NewTopic(topic, kafkaConfigData.getNumOfPartitions(),
                        kafkaConfigData.getReplicationFactor())).collect(Collectors.toList());
        return adminClient.createTopics(kafkaTopics);
    }

    private Collection<TopicListing> getTopics() {
        Collection<TopicListing> topics;
        try {
            topics = retryTemplate.execute(this::getKafkaTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry reading kafka topic(s)!", t);
        }
        return topics;
    }

    private Collection<TopicListing> getKafkaTopics(RetryContext retryContext) throws ExecutionException, InterruptedException {
        return adminClient.listTopics().listings().get();
    }

    public void checkTopicsCreated() {
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
            while (!isTopicCreated(topics, topic)) {
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTimeMs);
                sleepTimeMs += multiplier;
                topics = getTopics();
            }
        }
    }

    private boolean isTopicCreated(Collection<TopicListing> topics, String topic) {
        return Optional.ofNullable(topics)
                .map(topicListingCollection -> topicListingCollection.stream()
                        .anyMatch(topicListing -> topicListing.name().equals(topic)))
                .orElse(false);
    }

    private void checkMaxRetry(int retry, Integer maxRetry) {
        if (retry > maxRetry) {
            throw new KafkaClientException("Reached max number of retry reading kafka topic(s)!");
        }
    }

    private void sleep(Long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error while sleeping for waiting new created topics!");
        }
    }

    public void checkSchemaRegistry() {
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        int multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMs = retryConfigData.getSleepTimeMs();
        while (!getSchemaRegistryStatus().is2xxSuccessful()) {
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTimeMs);
            sleepTimeMs += multiplier;
        }
    }

    private HttpStatus getSchemaRegistryStatus() {
        try {
            return webClient.method(HttpMethod.GET)
                    .uri(kafkaConfigData.getSchemaRegistryUrl())
                    .retrieve()
                    .toBodilessEntity()
                    .blockOptional()
                    .map(ResponseEntity::getStatusCode)
                    .orElseGet(() -> HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }
}
