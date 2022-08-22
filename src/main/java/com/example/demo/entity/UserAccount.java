package com.example.demo.entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class UserAccount extends org.springframework.security.core.userdetails.User {

    @Getter
    private final User user;

    public UserAccount(User user) {
        super(user.getUserName(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))); // (2)
        this.user = user;
    }
}
