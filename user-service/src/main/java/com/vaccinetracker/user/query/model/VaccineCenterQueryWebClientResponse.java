package com.vaccinetracker.user.query.model;

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
public class VaccineCenterQueryWebClientResponse {

    private String id;
    private String name;
    private String address;
    private List<VaccineStock> vaccineStocks;
}
