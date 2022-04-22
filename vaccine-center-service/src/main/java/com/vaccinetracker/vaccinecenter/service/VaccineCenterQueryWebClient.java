package com.vaccinetracker.vaccinecenter.service;

import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponseModel;

public interface VaccineCenterQueryWebClient {

    VaccineCenterQueryWebClientResponseModel getVaccineCenterById(String id);
}
