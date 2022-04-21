package com.vaccinetracker.elastic.query.client.repository;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface VaccineCenterElasticsearchQueryRepository extends ElasticsearchRepository<VaccineCenterIndexModel, String> {

    @Query("{\"multi_match\" : { \"query\": \"?0\", \"fields\": [ \"name\", \"address\"]}}")
    List<VaccineCenterIndexModel> findByNameOrAddress(String text);
}