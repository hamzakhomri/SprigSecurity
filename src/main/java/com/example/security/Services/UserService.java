package com.example.security.Services;

import com.example.security.Repository.UserRepository;
import com.example.security.dto.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Userr createUser(Userr userr) throws UnknownHostException, SocketException {
        try {
            System.out.println("USer was created");
            return userRepository.save(userr);
        }catch (Exception e){
            return null;

        }
    }

    @Override
    public List<Userr> GetAllUsers() {
        System.out.println("find All");
        return userRepository.findAll();
    }
}
