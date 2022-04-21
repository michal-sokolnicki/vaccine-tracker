package com.vaccinetracker.query.service;

import com.vaccinetracker.query.model.VaccineCenterQueryServiceResponseModel;

import java.util.List;

public interface VaccineCenterQueryService {

    VaccineCenterQueryServiceResponseModel getVaccineCenterById(String id);
    List<VaccineCenterQueryServiceResponseModel> searchByText(String text);
}
