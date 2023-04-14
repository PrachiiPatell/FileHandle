package io.getarrays.fileuploadanddownload.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.getarrays.fileuploadanddownload.model.File;


@Repository
public interface FileRepository extends MongoRepository<File, String> {
    // Find a file by filename
    File findByFilename(String filename);

    // Find files by content type
    List<File> findByContentType(String contentType);

    // Find all files
    List<File> findAll();

    // Delete a file by ID
    void deleteById(String fileId);
}