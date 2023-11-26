package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.service.FirebaseFileService;
import com.asdc.dalbites.util.UtilityFunctions;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import com.google.cloud.storage.StorageOptions;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseFileServiceImpl implements FirebaseFileService {
    private Storage storage;

    @Autowired
    ResourceLoader resourceLoader;
    
    @Autowired
    UtilityFunctions utilityFunctions;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            File file = ResourceUtils.getFile("classpath:dalbites-4237e-firebase-adminsdk-35gtm-5fb454d097.json");
            InputStream in = new FileInputStream(file);
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(in)).
                    setProjectId("dalbites-4237e").build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException{
        String imageName = generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of("dalbites-4237e.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getBytes());
        return utilityFunctions.getFirebaseStorageURL(imageName);
    }
    
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}