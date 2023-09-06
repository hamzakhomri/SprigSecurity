package com.example.security.Controllers;

import com.example.security.Config.JwtUtils;
import com.example.security.dao.UserDao;
import com.example.security.dto.AuthenticationRequest;
import com.example.security.dto.AuthenticationResponse;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")

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
                    System.out.println("authenticate");
                    return ResponseEntity.ok(jwtUtils.generateTokenForUser(user));
                }

            } catch (Exception e) {
                Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
                logger.error("Something wrong !!", e);
                return ResponseEntity.status(500).body("Email or Password don't Exist");
            }
        return ResponseEntity.status(400).body("Some error had occurred.");
    }

    @GetMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        System.out.println("Token was Refreshed");
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);

        Object subValue = expectedMap.get("sub");

        if (subValue != null) {
            String token = jwtUtils.doGenerateRefreshToken(expectedMap, subValue.toString());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid 'sub' claim in the token.");
        }
    }


    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();

        if (claims != null) {
            // Check if "sub" claim is present before adding it to the map
            Object subClaim = claims.get("sub");
            if (subClaim != null) {
                expectedMap.put("sub", subClaim);
            }

            // Add other claims to the map
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                if (!"sub".equals(entry.getKey())) {
                    expectedMap.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return expectedMap;
    }



}