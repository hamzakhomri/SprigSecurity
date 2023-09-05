package com.example.security.Services.Role;

import com.example.security.dto.Rolee;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public interface IRoleService {
    Rolee createRole (Rolee rolee) throws UnknownHostException, SocketException;
    List<Rolee> getallroles();
}
