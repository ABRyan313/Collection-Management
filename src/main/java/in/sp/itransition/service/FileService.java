package in.sp.itransition.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    // Save the file to the specified directory and return the relative path
    public String saveFile(MultipartFile file, String uploadDir) throws IOException {
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        // Create directories if they do not exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Define the target file path
        Path targetFilePath = uploadPath.resolve(fileName);

        // Save the file to the target location
        Files.write(targetFilePath, file.getBytes());

        // Return the relative path for storage in the database (optional, if needed)
        return targetFilePath.toString();
    }

    // Additional file operations like delete, validate, etc.
    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    // Method to validate the file type or size if needed
    public boolean validateFile(MultipartFile file) {
        // Example: Check if the file type is allowed (image or PDF)
        String contentType = file.getContentType();
        return contentType != null && (contentType.startsWith("image/") || contentType.equals("application/pdf"));
    }
}
