package com.vaccinetracker.elastic.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaccineStock {

    @Field
    private String name;

    @Field
    private Integer quantity;

    @Field
    private Integer reserve;
}
