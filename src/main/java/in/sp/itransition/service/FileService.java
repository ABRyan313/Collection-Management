package in.sp.itransition.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    // Save the file to the specified directory
    public String saveFile(MultipartFile file, String uploadDir) throws IOException {
        String fileName = file.getOriginalFilename();
        File targetFile = new File(uploadDir + File.separator + fileName);

        // Create directories if they do not exist
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        // Transfer the file to the target location
        file.transferTo(targetFile);
        return targetFile.getAbsolutePath();
    }

    // Additional file operations like delete, validate, etc.
}
