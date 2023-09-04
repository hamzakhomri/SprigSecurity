package com.example.security.Controllers;

import com.example.security.Config.JwtUtils;
import com.example.security.dao.UserDao;
import com.example.security.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Authentication Request");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserDetails user = userDao.findUserByEmail(request.getEmail());

            if (user != null) {
                String token = jwtUtils.generateTokenForUser(user);
                log.info("Token was created");
                return ResponseEntity.ok(token);
            } else {
                log.error("User not found");
            }
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
            logger.error("-_- / Authentication failed.", e);
            return ResponseEntity.status(500).body("-_- /Authentication failed. Email or password does not exist.");
        }

        return ResponseEntity.status(400).body("-_- /Authentication failed. Some error occurred.");
    }
}
