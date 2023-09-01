package com.example.security.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        String username = extractClaim(token, Claims::getSubject);
        if (username != null) {
            return username;
        } else {
            throw new UsernameNotFoundException("email not found in the token");
        }
    }
    public Date extractExpiration(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);

        if (expirationDate != null) {
            return expirationDate;
        } else {
            throw new ExpiredJwtException(null, null, "Token expiration date not found in the token");
        }
    }
    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);

        if (claims.containsKey(claimName)) {
            System.out.println("hasClaim true");
            return true;
        } else {
            System.out.println("hasClaim false");

            return false;
        }
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSignignKey).parseClaimsJws(token).getBody();
    }



    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return
                Jwts.builder() //crée un jeton JWT à l'aide de la classe Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .claim("authorities", userDetails.getAuthorities())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)))
                        .signWith(SignatureAlgorithm.HS256, jwtSignignKey)
                        .compact();
    }
    private String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }
    public String generateTokenForUser(UserDetails userDetails) {
        return generateToken(userDetails);
    }
    private Boolean isTokenExpired(String token) {
        try {
            Date expirationDate = extractExpiration(token); // Get the expiration date from the token
            Date currentDate = new Date(); // Get the current date and time

            long expirationTimeMillis = expirationDate.getTime();
            long currentTimeMillis = currentDate.getTime();

            long timeDifferenceMillis = expirationTimeMillis - currentTimeMillis; // Calculate the time difference in milliseconds
            long hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis); // Convert milliseconds to hours
            long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis) % 60; // Convert remaining milliseconds to minutes

            String timeDifference = String.format("%02dH:%02dM", hours, minutes); // Format hours and minutes as HH:mm

            System.out.println("You Token was Expired after: " + timeDifference); // Print the time difference


            return expirationDate.before(currentDate);
        } catch (ExpiredJwtException ex) {
            System.out.println("Token Expired");
            return false;
        }
    }
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        System.out.println("Hello "+email+" welcome to your token");
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }









}