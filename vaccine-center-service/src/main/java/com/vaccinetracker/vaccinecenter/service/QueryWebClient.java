package com.vaccinetracker.vaccinecenter.service;

import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponse;

public interface QueryWebClient {

    VaccineCenterQueryWebClientResponse getVaccineCenterById(String id);
}
