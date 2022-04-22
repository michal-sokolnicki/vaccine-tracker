package com.vaccinetracker.elastic.query.client.repository;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;

import java.util.List;

public interface CustomizedVaccineCenterElasticsearchQueryRepository {

    List<VaccineCenterIndexModel> findByTextWithPositiveQuantities(String text);
}
