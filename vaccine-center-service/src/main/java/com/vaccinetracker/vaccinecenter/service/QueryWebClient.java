package com.vaccinetracker.vaccinecenter.service;

import com.vaccinetracker.webclient.query.model.VaccineCenterQueryWebClientResponse;

public interface QueryWebClient {

    VaccineCenterQueryWebClientResponse getVaccineCenterById(String id);
}
