package com.openclassroom.apiRentalApp.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SpringSecurityConfig {

	private String jwtKey = "7WppLoEmKWMSIbKSE8u2moQZ4d0g+oqZnXZMx2zas2nbAWqWQXByz61jr/tNDhoDZphwS4fPjwkVIKprMwIpbpH5wMQvUkztIx2JEKv3cjCvUWxr0Lm/iveAeP43gV6CFThz8ubB/2iSWfEEta89nAB/CNBSklb9juM/gKRvNb2Br+VuacrpZk8G3znGmXLKhkx0vneisXyYAjuVwqPuYQ/VEDEp+qyqbjAUtt2bD+R8Q49hy4DzFI1SCD4p9BPHZ674lMu+XJxP4Ie3telBogMxUJ9gy8Qw9vouXePpWVO0gED27y7OcwgDEhoT3lvwBgGzbEuz2B7fVhOzKREF+bywP2F+oD9gv3OizC73R/8=";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				.httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}