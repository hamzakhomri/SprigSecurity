package com.example.security.Controllers;

import com.example.security.Services.Role.IRoleService;
import com.example.security.dto.Rolee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {
    @Autowired
    IRoleService iRoleService;
    @PostMapping()
    public Rolee createRole(@RequestBody Rolee rolee) throws SocketException, UnknownHostException{
        Rolee rolee1 =iRoleService.createRole(rolee);
        if (rolee1 == null){
            System.out.println("role is null");
            return null;
        }
        else {
            return rolee;
        }
    }
    @GetMapping()
    public List<Rolee> GetAll(){
        return iRoleService.getallroles();
    }
}
