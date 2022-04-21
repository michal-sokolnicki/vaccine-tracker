package com.vaccinetracker.booking.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryWebClientRequestModel {

    private String id;
    private String govId;
    private String status;
    private String text;
}
