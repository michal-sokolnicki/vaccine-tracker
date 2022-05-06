package com.vaccinetracker.security.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {

    private UserContext() {}

    public static UserDetails getUserDetails() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
                        .getAuthentication();
        return (UserDetails) usernamePasswordAuthenticationToken.getPrincipal();
    }
}
