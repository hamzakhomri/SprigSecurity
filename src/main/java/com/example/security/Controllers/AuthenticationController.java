package com.example.security.Controllers;

import com.example.security.Config.JwtUtils;
import com.example.security.dao.UserDao;
import com.example.security.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            final UserDetails user = userDao.finduserbyemail(request.getEmail());

            if (user != null) {
                System.out.println("token was created");
                return ResponseEntity.ok(jwtUtils.generateTokenForUser(user));
            }else {
                System.out.println("Creation Token Failed");
            }

        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
            logger.error("Something wrong !!", e);
            return ResponseEntity.status(500).body(request.getEmail()+" or "+request.getPassword()+" Don't Exist");
        }
        return ResponseEntity.status(400).body("Some error had occurred.");
    }
}