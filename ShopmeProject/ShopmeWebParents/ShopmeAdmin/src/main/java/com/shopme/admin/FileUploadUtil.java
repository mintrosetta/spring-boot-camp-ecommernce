package com.shopme.admin;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) {
        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // InputStream read data with format bytes
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void removeFile(String dir, String fileName) {
        try {
            Path path = Paths.get(dir);
            Path pathFile = path.resolve(fileName);

            if (Files.exists(pathFile)) {
                Files.delete(pathFile);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
