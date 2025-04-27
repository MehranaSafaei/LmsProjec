package org.example.lms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.lms.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Jwt {

    private static final String SECRET_KEY = "secret";

    public String generateToken(User user) {
        String jwt = Jwts.builder().
                setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 86400000))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
        return jwt;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
    }

}
