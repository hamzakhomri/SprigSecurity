package com.example.security.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserDao {
    @Autowired
    @Lazy
    PasswordEncoder encoder;
    private static List<UserDetails> APPLICATION_USERS;
    public UserDetails finduserbyemail(String email){
        String password = false ? encoder.encode("password") : "password";

        this.APPLICATION_USERS = List.of(
                new User("user@gmail.com", password, List.of(new SimpleGrantedAuthority("ROLE ADMIN"),new SimpleGrantedAuthority("ROLE ENG")) )
        );
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(email))
                .findFirst()
                .orElseThrow(()-> new UsernameNotFoundException("EMail No Found"));
    }

    public UserDetails finduserbyPassword(String password){
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(password))
                .findFirst()
                .orElseThrow(()-> new UsernameNotFoundException("No password FOUnd"))
                ;
    }
}