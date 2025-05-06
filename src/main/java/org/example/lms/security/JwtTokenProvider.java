package org.example.lms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.lms.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwtToken.message}")
    private String message;

    public Map<String, String> generateToken(UserDetails userDetails) {
        String jwtToken = Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();

        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", message);
        return jwtTokenGen;
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        List<SimpleGrantedAuthority> authorities = getRoles(token).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        Claims claims = getClaims(token);
        return claims.get("roles", List.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
