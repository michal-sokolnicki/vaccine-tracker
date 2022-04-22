package com.vaccinetracker.elastic.query.client.repository;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineCenterElasticsearchQueryRepository extends ElasticsearchRepository<VaccineCenterIndexModel, String>,
        CustomizedVaccineCenterElasticsearchQueryRepository {
}
