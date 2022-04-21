package com.vaccinetracker.elastic.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
public class VaccineStock {

    @Field
    private String name;

    @Field
    private Integer quantity;
}
