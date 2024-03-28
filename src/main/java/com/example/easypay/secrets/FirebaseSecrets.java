package com.example.easypay.secrets;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("firebase")
public record FirebaseSecrets(String privatekeyid,String privatekey) {
}
