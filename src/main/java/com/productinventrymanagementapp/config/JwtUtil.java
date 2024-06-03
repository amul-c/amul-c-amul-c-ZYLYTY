package com.productinventrymanagementapp.config;

import com.productinventrymanagementapp.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {
	public String generateToken(Authentication authentication) {
		String jwt = null;
        if (null != authentication) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().issuer("Product Inventory Management").subject("JWT Token")
                    .claim("username", authentication.getName())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1800000))
                    .signWith(key).compact();
            
        }
        return jwt;
	}
}
