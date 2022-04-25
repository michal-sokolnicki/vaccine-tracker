package com.vaccinetracker.vaccinecenter.service.transformer;

import com.vaccinetracker.elastic.model.impl.VaccineCenterIndexModel;
import com.vaccinetracker.vaccinecenter.model.VaccineCenterRequest;
import org.springframework.stereotype.Component;

@Component
public class VaccineCenterToIndexModelTransformer {

    public VaccineCenterIndexModel getVaccineCenterIndexModel(String id, VaccineCenterRequest vaccineCenterRequest) {
        return VaccineCenterIndexModel.builder()
                .vaccineCenterId(id)
                .name(vaccineCenterRequest.getName())
                .address(vaccineCenterRequest.getAddress())
                .vaccineStocks(vaccineCenterRequest.getVaccineStocks())
                .build();
    }
}
