package com.openclassroom.apiRentalApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class JwtConfig {

    private final String jwtKey = "7WppLoEmKWMSIbKSE8u2moQZ4d0g+oqZnXZMx2zas2nbAWqWQXByz61jr/tNDhoDZphwS4fPjwkVIKprMwIpbpH5wMQvUkztIx2JEKv3cjCvUWxr0Lm/iveAeP43gV6CFThz8ubB/2iSWfEEta89nAB/CNBSklb9juM/gKRvNb2Br+VuacrpZk8G3znGmXLKhkx0vneisXyYAjuVwqPuYQ/VEDEp+qyqbjAUtt2bD+R8Q49hy4DzFI1SCD4p9BPHZ674lMu+XJxP4Ie3telBogMxUJ9gy8Qw9vouXePpWVO0gED27y7OcwgDEhoT3lvwBgGzbEuz2B7fVhOzKREF+bywP2F+oD9gv3OizC73R/8=";

    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(), "HmacSHA256");
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }
}