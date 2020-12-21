package com.example.auth_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String keyStorePath;
    private String keyStorePassword;
    private String keyAlias;
    private Integer expiration;   
}
