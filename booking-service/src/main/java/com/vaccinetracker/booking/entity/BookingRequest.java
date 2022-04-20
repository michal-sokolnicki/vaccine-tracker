package com.vaccinetracker.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    private String firstname;
    private String surname;
    private String govId;
    private String vaccineCenter;
    private String address;
    private String vaccineType;
    private String term;
}
