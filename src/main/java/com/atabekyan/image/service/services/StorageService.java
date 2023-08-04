package com.atabekyan.image.service.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    Path load(String filename);

    void deleteFile(String url);

    Path getRootLocation();
}
