package com.vaccinetracker.elastic.index.client.repository;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingElasticsearchIndexRepository extends ElasticsearchRepository<BookingIndexModel, String> {
}
