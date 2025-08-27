package com.example.wini.global.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record AuthMember(Long memberId) implements UserDetails {

    public static AuthMember of(Long memberId) {
        return new AuthMember(memberId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.memberId.toString();
    }
}
