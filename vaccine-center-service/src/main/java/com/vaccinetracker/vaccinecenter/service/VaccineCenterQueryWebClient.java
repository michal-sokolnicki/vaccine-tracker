package com.vaccinetracker.vaccinecenter.service;

import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponse;

public interface VaccineCenterQueryWebClient {

    VaccineCenterQueryWebClientResponse getVaccineCenterById(String id);
}
