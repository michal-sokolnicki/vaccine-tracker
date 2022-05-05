package com.vaccinetracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String govId;
    private String email;
}
