package com.vaccinetracker.vaccinecenter.model;

import com.vaccinetracker.elastic.model.entity.VaccineStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccineCenterRequest {

    private String name;
    private String address;
    private List<VaccineStock> vaccineStocks;
}
