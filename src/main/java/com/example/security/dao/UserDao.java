package com.example.security.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserDao {
    private final static List<UserDetails> APPLICATION_USERS= Arrays.asList(
            new User(
                    "hamza.khomri@gmail.com",
                    "hamza",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE ADMIN"))
            ),
            new User(
                    "user.adresse@gmail.com",
                    "password",
                    Collections.singleton(new SimpleGrantedAuthority("USER"))
            )
    );
    public UserDetails finduserbyemail(String email){
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(email))
                .findFirst()
                .orElseThrow(()-> new UsernameNotFoundException("No USer FOUnd"))
        ;
    }
}
