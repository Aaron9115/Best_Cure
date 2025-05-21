package com.islington.util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageUtil {
    
    public String getImageNameFromPart(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "product_" + System.currentTimeMillis() + ".jpg";
    }

    public boolean uploadImage(Part image, String basePath, String subdirectory, String fileName) throws IOException {
        // Create the full upload path
        String uploadPath = basePath + "resources" + File.separator + subdirectory;
        
        // Create directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // Construct the full file path
        String filePath = uploadPath + File.separator + fileName;
        
        // Save the file
        try (InputStream fileContent = image.getInputStream()) {
            Path destination = Paths.get(filePath);
            Files.copy(fileContent, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        
        return true;
    }

    public String getSavePath(String saveFolder) {
        return "C:/Users/User/eclipse-workspace/Best_Cure/src/main/webapp/resources/"+saveFolder+"/";
    }
}