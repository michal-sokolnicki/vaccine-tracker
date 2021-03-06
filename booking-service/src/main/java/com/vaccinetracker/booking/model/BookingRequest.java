package com.vaccinetracker.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    private String vaccineCenterId;
    private String vaccineCenterName;
    private String address;
    private String vaccineType;
    private String term;
    private String status;
}
