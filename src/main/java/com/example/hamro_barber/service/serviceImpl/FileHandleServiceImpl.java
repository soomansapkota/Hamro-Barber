package com.example.hamro_barber.service.serviceImpl;

import com.example.hamro_barber.constants.FileType;
import com.example.hamro_barber.model.User;
import com.example.hamro_barber.exception.CustomException;
import com.example.hamro_barber.service.FileHandleService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileHandleServiceImpl implements FileHandleService {

    @Value("${file.upload.directory}")
    private String UPLOAD_PATH;
    @Override
    public String uploadImage(MultipartFile file, User user) {
        String random = null;
        try {


            random = UUID.randomUUID() + ".jpg";
            Path filePath = Paths.get(UPLOAD_PATH+"/"+ FileType.USER_IMAGES.getValue());
            Path fileNameAndPath = filePath.resolve(filePath + "/" + random);

            Files.write(fileNameAndPath, file.getBytes());

            if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
                String deletePrevImage = user.getImageUrl();
                fileNameAndPath = filePath.resolve(deletePrevImage);
                Files.deleteIfExists(fileNameAndPath);
            }
        } catch (SizeLimitExceededException e) {
            throw new CustomException("Upload file can not exceed 500KB");
        } catch (FileAlreadyExistsException e) {
            uploadImage(file, user);
        } catch (Exception e) {
            throw new CustomException("Failed to upload image: " + e.getMessage());
        }
        return random;
    }

    @Override
    public Path load(String filename) {
        Path rootLocation = Path.of(UPLOAD_PATH);
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadImage(String imageName) {

        try {
            Path file= load(FileType.USER_IMAGES.getValue()+"/"+imageName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else if (!resource.exists()) {
                throw new CustomException("No Image");
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            Path file = load(filePath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists()) {
                File file1 = file.toFile();
                boolean success = file1.delete();
            } else {
                throw new CustomException("File doesnot exists");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
