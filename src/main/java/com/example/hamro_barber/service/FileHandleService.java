package com.example.hamro_barber.service;

import com.example.hamro_barber.model.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileHandleService {
    String uploadImage(MultipartFile file, User user);
    Path load(String filename);
    Resource loadImage(String imageUrl);

    void deleteFile(String filePath);
}
