package com.vaccinetracker.security.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static com.vaccinetracker.security.Constants.NA;

@Getter
@Builder
public class UserDetails {

    private String username;
    private String firstname;
    private String lastname;
    private String govId;
    private Collection<? extends GrantedAuthority> authorities;

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return NA;
    }
}
