package com.vaccinetracker.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestUpdate {

    private String vaccineCenter;
    private String address;
    private String vaccineType;
    private String term;
    private String status;
}
