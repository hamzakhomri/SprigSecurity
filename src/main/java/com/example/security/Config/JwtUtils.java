package com.example.security.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtUtils {
    private String jwtSignignKey="secret";
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public boolean hasclaim(String token , String clainName){
        final Claims claims=extraxtAllClaims(token);
        return claims.get(clainName)!= null;
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extraxtAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extraxtAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(String.valueOf(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();    }
    private Boolean isTokenExpired(String token){return extractExpiration(token).before(new Date());}

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userDetails);
    }
    private String generateToken(UserDetails userDetails, Map<String,Object> claims){
        return createToken(claims,userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities",userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(25)))
                .signWith(SignatureAlgorithm.HS256,jwtSignignKey).compact();
    }
    public Boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
