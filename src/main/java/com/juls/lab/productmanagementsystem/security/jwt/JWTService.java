package com.juls.lab.productmanagementsystem.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-token.expiration}")
    public Long refreshExpiration;

    // Generate toke for user authentication
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generate token with extra claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    // Generate refresh token
    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    // Build JWT Token
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration){
        return Jwts.builder()
                .addClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token){
            try{
                Jwts.parserBuilder()
                        .setSigningKey(getSigninKey())
                        .build()
                        .parseClaimsJws(token);
                return true;
    }
            catch (JwtException | IllegalArgumentException e){
                return false;
            }
    }

    // Extract username from token
    public String extractUsername(String token){
            return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }



    // Extract specific claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token) // Parses and validates the token
                .getBody(); // Extracts the claims (payload) from the token
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate token for specific user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    // Get signing key
    private SecretKey getSigninKey(){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
