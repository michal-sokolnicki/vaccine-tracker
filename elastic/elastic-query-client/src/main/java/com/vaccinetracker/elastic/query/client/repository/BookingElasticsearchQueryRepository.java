package com.vaccinetracker.elastic.query.client.repository;

import com.vaccinetracker.elastic.model.impl.BookingIndexModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookingElasticsearchQueryRepository extends ElasticsearchRepository<BookingIndexModel, String> {

    List<BookingIndexModel> findByGovId(String govId);
    List<BookingIndexModel> findByGovIdAndStatus(String govId, String status);

    @Query("{\"multi_match\" : { \"query\": \"?0\", \"fields\": [ \"vaccine_center_name\", \"vaccine_type\"]}}")
    List<BookingIndexModel> findByVaccineCenterOrVaccineType(String text);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"gov_id\":\"?0\"}},{\"range\":{\"term\":{\"gte\":\"?1\",\"lte\":\"?2\"}}}]}}")
    List<BookingIndexModel> findByGovIdWithDateRange(String govId, String startDate, String endDate);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"vaccine_center_id\":\"?0\"}},{\"range\":{\"term\":{\"gte\":\"?1\",\"lte\":\"?2\"}}}]}}")
    List<BookingIndexModel> findByVaccineCenterIdWithDateRange(String vaccineCenterId, String startDate, String endDate);
}
