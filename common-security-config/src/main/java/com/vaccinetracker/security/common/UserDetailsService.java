package com.vaccinetracker.security.common;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDetailsService {

    private static final String USERNAME_CLAIM = "preferred_username";
    private static final String FIRSTNAME_CLAIM = "given_name";
    private static final String LASTNAME_CLAIM = "family_name";
    private static final String GOV_ID_CLAIM = "gov_id";
    private static final String VACCINE_CENTER_ID_CLAIM = "vaccine_center_id";

    public UserDetails getUserDetails(Map<String, Object> claims) {
        return UserDetails.builder()
                .username((String) claims.get(USERNAME_CLAIM))
                .firstname((String) claims.get(FIRSTNAME_CLAIM))
                .lastname((String) claims.get(LASTNAME_CLAIM))
                .govId((String) claims.get(GOV_ID_CLAIM))
                .vaccineCenterId((String) claims.get(VACCINE_CENTER_ID_CLAIM))
                .build();
    }
}
