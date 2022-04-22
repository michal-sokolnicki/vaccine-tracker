package com.vaccinetracker.elastic.index.client.service.impl;

import com.vaccinetracker.elastic.index.client.service.repository.BookingElasticsearchIndexRepository;
import com.vaccinetracker.elastic.index.client.service.ElasticIndexClient;
import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookingElasticIndexClient implements ElasticIndexClient<BookingIndexModel> {

    private final BookingElasticsearchIndexRepository bookingElasticsearchIndexRepository;

    public BookingElasticIndexClient(BookingElasticsearchIndexRepository bookingElasticsearchIndexRepository) {
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
        List<String> ids = bookingIndexModels.stream()
                .map(BookingIndexModel::getId)
                .collect(Collectors.toList());
        log.info("Documents indexed successfully with type: {} and ids: {}", BookingIndexModel.class.getName(), ids);
        return ids;
    }
}
