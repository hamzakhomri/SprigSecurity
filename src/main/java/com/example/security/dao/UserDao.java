package com.example.security.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao {

    private final List<UserDetails> APPLICATION_USERS = new ArrayList<>();

    public UserDao() {
        // Initialize with some sample user data
        APPLICATION_USERS.add(User.builder()
                .username("abc@abc")
                .password("abc")
                .authorities("ROLE_ADMIN")
                .build());
    }

    public UserDetails findUserByEmail(String email) {
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}