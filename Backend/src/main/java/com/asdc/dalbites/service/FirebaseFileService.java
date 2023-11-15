package com.asdc.dalbites.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FirebaseFileService {
    public String uploadFile(MultipartFile file) throws IOException;
}
