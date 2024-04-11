package com.example.easypay.config.twilio;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("twilio")
public record TwilioConfigurationProperties(String accountSid, String authToken, String serviceSid) {
}
