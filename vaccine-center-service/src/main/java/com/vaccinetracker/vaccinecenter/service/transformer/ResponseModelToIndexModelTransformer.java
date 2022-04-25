package com.vaccinetracker.vaccinecenter.service.transformer;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.vaccinecenter.query.model.VaccineCenterQueryWebClientResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseModelToIndexModelTransformer {

    public VaccineCenterIndexModel getVaccineCenterIndexModel(VaccineCenterQueryWebClientResponse responseModel) {
        return VaccineCenterIndexModel.builder()
                .vaccineCenterId(responseModel.getId())
                .name(responseModel.getName())
                .address(responseModel.getAddress())
                .vaccineStocks(responseModel.getVaccineStocks())
                .build();
    }
}
