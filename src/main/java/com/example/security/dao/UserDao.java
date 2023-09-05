package com.example.security.dao;

import com.example.security.Repository.UserRepository;
import com.example.security.dto.Userr;
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
    @Autowired
    private static List<UserDetails> APPLICATION_USERS;
    @Autowired
    private UserRepository userRepository;
    public UserDetails finduserbyemail(String email){
        Userr userr = userRepository.findByLogin(email);
        if (userr == null)
            throw new UsernameNotFoundException("EMail No Found");
        return  new User(userr.getLogin(), userr.getPassword(), List.of(new SimpleGrantedAuthority("ROLE ADMIN"),new SimpleGrantedAuthority("ROLE ENG")) );
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