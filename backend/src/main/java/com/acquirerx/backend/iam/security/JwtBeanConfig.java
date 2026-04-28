package com.acquirerx.backend.iam.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtBeanConfig {

    @Bean
    public JwtUtil jwtUtil(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-ttl:3600}") long accessTtl
    ) {
        return new JwtUtil(secret, accessTtl);
    }
}
