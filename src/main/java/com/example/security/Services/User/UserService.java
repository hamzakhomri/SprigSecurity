package com.example.security.Services.User;

import com.example.security.Repository.UserRepository;
import com.example.security.dto.Userr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    @Override
    public Userr createUser(Userr userr) throws UnknownHostException, SocketException {
        try {
            log.info("User was created");
            userr.setPassword(passwordEncoder.encode(userr.getPassword()));
            return userRepository.save(userr);
        }catch (Exception e){
            log.error("error from create User : "+e);
            return null;
        }
    }

    @Override
    public List<Userr> GetAllUsers() {
        System.out.println("find All");
        return userRepository.findAll();
    }
}
