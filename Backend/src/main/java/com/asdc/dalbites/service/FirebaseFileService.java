package com.asdc.dalbites.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for interacting with Firebase Cloud Storage to handle file uploads.
 */
@Service
public interface FirebaseFileService {

    /**
     * Uploads a file to Firebase Cloud Storage.
     *
     * @param file The file to be uploaded.
     * @return A string representing the download URL or identifier of the uploaded file.
     * @throws IOException If an I/O error occurs during the file upload process.
     */
    public String uploadFile(MultipartFile file) throws IOException;
}
