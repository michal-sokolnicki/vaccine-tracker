package com.vaccinetracker.elastic.query.client.repository;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineCenterElasticsearchQueryRepository extends ElasticsearchRepository<VaccineCenterIndexModel, String>,
        CustomizedVaccineCenterElasticsearchQueryRepository {
}
