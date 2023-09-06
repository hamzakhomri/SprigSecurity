package com.example.security.dao;

import com.example.security.Repository.RoleRepository;
import com.example.security.Repository.UserRepository;
import com.example.security.dto.Rolee;
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
import java.util.stream.Collectors;

@Repository
public class UserDao {
    @Autowired
    @Lazy
    PasswordEncoder encoder;
    @Autowired
    private static List<UserDetails> APPLICATION_USERS;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public UserDetails finduserbyemail(String email){

        Userr userr = userRepository.findByLogin(email);

        if (userr == null)
            throw new UsernameNotFoundException("Email No Found");
        List<Rolee> roles = userr.getRoles();
        return  new User(
                userr.getLogin(),
                userr.getPassword(),
                roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList())
        );
    }

}