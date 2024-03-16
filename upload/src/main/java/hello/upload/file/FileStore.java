package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDirectory;

    public String getFullPath(String filename) {
        return String.join("", fileDirectory, filename);
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(this::storeFile)
                .toList();
    }

    public UploadFile storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);

        try {
            multipartFile.transferTo(new File(getFullPath(storeFilename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new UploadFile(originalFilename, storeFilename);
    }

    private String extractExtension(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position + 1);
    }

    private String createStoreFilename(String originalFilename) {
        String extension = extractExtension(originalFilename);
        String uuid = UUID.randomUUID()
                .toString();
        return String.join(".", uuid, extension);
    }
}
