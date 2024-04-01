package com.example.easypay.secrets;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("email")
public record EmailConfigurationProperties(String username, String password) {
}
