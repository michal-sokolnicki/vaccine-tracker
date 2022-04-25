package com.vaccinetracker.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingQueryRequest {

    private String govId;
    private String vaccineCenterId;
    private String from;
    private String to;
}
