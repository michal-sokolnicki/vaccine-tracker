package com.vaccinetracker.elastic.query.client.repository;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookingElasticsearchQueryRepository extends ElasticsearchRepository<BookingIndexModel, String> {

    List<BookingIndexModel> findByGovId(String govId);
    List<BookingIndexModel> findByGovIdAndStatus(String govId, String status);

    @Query("{\"multi_match\" : { \"query\": \"?0\", \"fields\": [ \"vaccine_center\", \"vaccine_type\"]}}")
    List<BookingIndexModel> findByVaccineCenterOrVaccineType(String text);
}
