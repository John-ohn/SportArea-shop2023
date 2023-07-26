package com.sportArea.security;

import com.sportArea.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.security.key}")
    private String securityKey;

    @Value("${jwt.expiration}")
    private long validityMilliseconds;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

    }

    @PostConstruct
    protected void init() {
        securityKey = Base64.getEncoder().encodeToString(securityKey.getBytes());
    }

    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMilliseconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
//            System.out.println(e.getMessage());
//            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody().getSubject();
    }

     public String resolveToken(HttpServletRequest request){
        return request.getHeader(authorizationHeader);
     }

}
