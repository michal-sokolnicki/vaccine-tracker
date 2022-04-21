package com.vaccinetracker.elastic.index.client.service.impl;

import com.vaccinetracker.elastic.index.client.exception.ElasticIndexClientException;
import com.vaccinetracker.elastic.index.client.repository.BookingElasticsearchIndexRepository;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
public class BookingElasticRepositoryIndexClient implements ElasticIndexClient<BookingIndexModel> {

    private final BookingElasticsearchIndexRepository bookingElasticsearchIndexRepository;

    public BookingElasticRepositoryIndexClient(BookingElasticsearchIndexRepository bookingElasticsearchIndexRepository) {
        this.bookingElasticsearchIndexRepository = bookingElasticsearchIndexRepository;
    }

    @Override
    public String save(BookingIndexModel document) {
        BookingIndexModel bookingIndexModel = bookingElasticsearchIndexRepository.save(document);
        String id = bookingIndexModel.getId();
        log.info("Document indexed successfully with type: {} and id: {}", BookingIndexModel.class.getName(), id);
        return id;
    }

    @Override
    public List<String> saveAll(List<BookingIndexModel> documents) {
        List<BookingIndexModel> bookingIndexModels = (List<BookingIndexModel>) bookingElasticsearchIndexRepository.saveAll(documents);
        List<String> ids = bookingIndexModels.stream().map(BookingIndexModel::getId).collect(Collectors.toList());
        log.info("Documents indexed successfully with type: {} and ids: {}", BookingIndexModel.class.getName(), ids);
        return ids;
    }
}
