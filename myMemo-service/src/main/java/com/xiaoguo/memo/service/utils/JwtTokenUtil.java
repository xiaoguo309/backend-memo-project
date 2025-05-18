package com.xiaoguo.memo.service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for JWT token operations
 */
@Slf4j
public class JwtTokenUtil {

    private static final String SECRET = "mymemo-secret-key-should-be-at-least-256-bits-long-for-hs256";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION = 86400000; // 24 hours in milliseconds

    /**
     * Generate a JWT token
     * @param userId user ID
     * @param days token validity in days
     * @return JWT token
     */
    public static String generateToken(String userId, int days) {
        long expirationTime = days > 0 ? days * 24 * 60 * 60 * 1000L : EXPIRATION;
        Date expiration = new Date(System.currentTimeMillis() + expirationTime);
        
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate token and extract user ID
     * @param token JWT token
     * @return user ID if token is valid, null otherwise
     */
    public static String validateTokenAndGetUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired: {}", e.getMessage());
        } catch (Exception e) {
            log.error("JWT token validation error: {}", e.getMessage());
        }
        return null;
    }
} 