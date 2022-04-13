package com.vaccinetracker.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Long vaccineCenterId;
    private String vaccineType;
    private String date;
    private String time;
}
