package com.vaccinetracker.elastic.model.impl;

import com.vaccinetracker.elastic.model.IndexModel;
import com.vaccinetracker.elastic.model.entity.VaccineStock;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@Document(indexName = "#{@elasticConfigData.vaccineCenterIndexName}")
public class VaccineCenterIndexModel implements IndexModel {

    @Id
    @Field("vaccine_center_id")
    private String vaccineCenterId;

    @Field
    private String name;

    @Field
    private String address;

    @Field(name = "vaccine_stock", type = FieldType.Nested)
    private List<VaccineStock> vaccineStocks;

    @Override
    public String getId() {
        return vaccineCenterId;
    }
}
