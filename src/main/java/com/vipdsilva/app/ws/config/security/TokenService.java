package com.vipdsilva.app.ws.config.security;

import java.util.Date;

import com.vipdsilva.app.ws.entities.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${apisw.jwt.expirationTime}")
    private String expirationTime;
    
    @Value("${apisw.jwt.secret}")
    private String secret;


    public String generateToken(Authentication authentication) {
        User logged = (User) authentication.getPrincipal();

        Date today = new Date();
        Date expDate = new Date(today.getTime() + Long.parseLong(expirationTime));
        
        return Jwts.builder()
            .setIssuer("API de Star Wars")
            .setSubject(logged.getId().toString())
            .setIssuedAt(today)
            .setExpiration(expDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }


    public boolean isTokenValid(String token) {

        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Long getUserId(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        return Long.parseLong(claims.getBody().getSubject());
    }

}
