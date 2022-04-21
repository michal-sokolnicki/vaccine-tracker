package com.vaccinetracker.query.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingQueryServiceResponseModel {

    private String id;
    private String firstname;
    private String surname;
    private String govId;
    private String vaccineCenter;
    private String address;
    private String vaccineType;
    private String term;
    private String status;
}
