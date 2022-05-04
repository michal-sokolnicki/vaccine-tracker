package com.vaccinetracker.user.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryWebClientResponse {

    private String id;
    private String firstname;
    private String surname;
    private String govId;
    private String vaccineCenterId;
    private String vaccineCenterName;
    private String address;
    private String vaccineType;
    private String term;
    private String status;
}
