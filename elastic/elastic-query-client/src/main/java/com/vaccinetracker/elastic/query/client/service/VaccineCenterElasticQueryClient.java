package com.vaccinetracker.elastic.query.client.service;

import com.vaccinetracker.elastic.model.IndexModel;

import java.util.List;

public interface VaccineCenterElasticQueryClient<T extends IndexModel> {

    T getVaccineCenterById(String id);
    List<T> searchVaccineCentersByText(String text);
}
