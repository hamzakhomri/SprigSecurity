package com.example.security.Controllers;

import com.example.security.Services.IUserService;
import com.example.security.dto.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class USerController {
    @Autowired
    private IUserService iUserService;

    @PostMapping()
    public Userr createuser (@RequestBody Userr userr) throws SocketException, UnknownHostException {
        return iUserService.createUser(userr);
    }
    @GetMapping()
    public List<Userr> Getall (){
        System.out.println("Get aLL");
        return iUserService.GetAllUsers();
    }
}
