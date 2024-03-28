package com.example.easypay.services;


import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UploadService {


    private final Storage storage;

    public UploadService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public ArrayList<String> uploadImages(MultipartFile[] files)  {
        ArrayList<String> downloadUrls = new ArrayList<>();

        for (MultipartFile file : files) {

            String fileName = UUID.randomUUID().toString();

            BlobId blobId = BlobId.of("easypay-8e759.appspot.com", fileName); // Replace with your bucker name
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
            InputStream inputStream = UploadService.class.getClassLoader().getResourceAsStream("firebase.config.json"); // change the file name with your one
            Credentials credentials = null;
            try {
                credentials = GoogleCredentials.fromStream(inputStream);

                Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
                storage.create(blobInfo, file.getBytes());
                String downloadUrl = storage.get(blobId).signUrl(3600, TimeUnit.SECONDS).toString();
                downloadUrls.add(downloadUrl);
            } catch (IOException e) {

                log.error("Error uploading file ");
                downloadUrls.add("");
            }

        }

        return downloadUrls;
    }

}
