package com.vaccinetracker.elastic.index.client.repository;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineCenterElasticsearchIndexRepository extends ElasticsearchRepository<VaccineCenterIndexModel, String> {
}
