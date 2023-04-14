package io.getarrays.fileuploadanddownload.service;
import java.util.Optional;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.getarrays.fileuploadanddownload.model.File;
import io.getarrays.fileuploadanddownload.repository.FileRepository;

@Service
public class FileService {
	@Autowired
    private FileRepository fileRepository;

    public File uploadFile(String filename, String contentType,byte[] data) {
        File file = new File();
        file.setFilename(filename);
        file.setContentType(contentType);
        file.setData(data);
        return fileRepository.save(file);
    }

    public File downloadFile(String fileId) throws FileNotFoundException {
        Optional<File> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            return optionalFile.get();
        } else {
            throw new FileNotFoundException("File not found with ID: " + fileId);
        }
        }
    
//    public File downloadFileByFilename(String filename) throws IOException {
//        FileDocument fileDocument = fileRepository.findByFilename(filename);
//        if (fileDocument == null) {
//            throw new FileNotFoundException("File not found with filename: " + filename);
//        }
//        return new File(fileDocument.getFilePath());
//    }
}
