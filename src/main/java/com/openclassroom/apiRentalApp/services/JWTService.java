package com.openclassroom.apiRentalApp.services;

import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        try {
            Instant now = Instant.now();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.HOURS))
                    .subject(authentication.getName())
                    .claim("roles", authentication.getAuthorities())
                    .build();

            return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        } catch (JwtEncodingException e) {
            throw new RuntimeException("Failed to encode JWT", e);
        }
    }
}