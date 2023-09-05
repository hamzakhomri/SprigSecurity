package com.example.security.Services;

import com.example.security.dto.Userr;

import java.net.SocketException;
import java.net.UnknownHostException;

public interface IUserService {
    Userr createUser(Userr userr) throws UnknownHostException, SocketException;
}
