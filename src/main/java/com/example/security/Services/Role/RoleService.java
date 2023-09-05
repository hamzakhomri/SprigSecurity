package com.example.security.Services.Role;

import com.example.security.Repository.RoleRepository;
import com.example.security.dto.Rolee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@Service
@Slf4j
public class RoleService implements IRoleService{
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Rolee createRole(Rolee rolee) throws UnknownHostException, SocketException {
        try {
            log.info("Role was created");
            return roleRepository.save(rolee);
        }catch (Exception e){
            log.error("error in creation role : "+e);
            return null;
        }
    }

    @Override
    public List<Rolee> getallroles() {
        System.out.println("Get ALlRoles");
        return roleRepository.findAll();
    }

}
