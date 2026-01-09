package com.kh.chamreunvira.springboot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtil {

    @Value("${uploads.upload-dir}")
    public String UPLOAD_DIR;

    public String saveFile(MultipartFile file) throws Exception {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if(!Files.exists(uploadPath)) {
            Files.createFile(uploadPath);
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName).toAbsolutePath().normalize();
        Files.copy(file.getInputStream(), filePath , StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

}
