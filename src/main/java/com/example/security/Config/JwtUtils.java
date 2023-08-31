package com.example.security.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {

    private final String jwtSignignKey = "yourSecretKeyHere"; // Change this to your actual secret key
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

//                          2
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("extractClaim");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //                1
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSignignKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }
    public String generateTokenForUser(UserDetails userDetails) {
        return generateToken(userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return
                 Jwts.builder() //crée un jeton JWT à l'aide de la classe Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(25)))
                .signWith(SignatureAlgorithm.HS256, jwtSignignKey)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}