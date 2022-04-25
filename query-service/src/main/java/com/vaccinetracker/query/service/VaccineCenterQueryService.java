package com.vaccinetracker.query.service;

import com.vaccinetracker.query.model.VaccineCenterQueryResponse;

import java.util.List;

public interface VaccineCenterQueryService {

    VaccineCenterQueryResponse getVaccineCenterById(String id);
    List<VaccineCenterQueryResponse> searchByText(String text);
}
