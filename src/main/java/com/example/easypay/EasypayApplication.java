package com.example.easypay;

import com.example.easypay.secrets.FirebaseSecrets;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.FileInputStream;

@SpringBootApplication
@EnableConfigurationProperties(FirebaseSecrets.class)
public class EasypayApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EasypayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/firebase.config.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://easypay-8e759-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

    }
}
