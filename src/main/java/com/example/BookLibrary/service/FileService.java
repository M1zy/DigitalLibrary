package com.example.BookLibrary.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.example.BookLibrary.exception.RecordNotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${user.dir}")
    private String uploadDir;

    public void uploadFile(MultipartFile file) {

        try {
            if(!file.isEmpty()){
            String copyLocation = uploadDir+"/src/main/webapp/resources/BookFiles/";
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(copyLocation+file.getOriginalFilename()));}
        } catch (Exception e) {
            e.printStackTrace();
            throw new RecordNotFoundException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }
}