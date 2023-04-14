package io.getarrays.fileuploadanddownload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders; // Import the HttpHeaders class
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // Import the MediaType class
import org.springframework.http.ResponseEntity; // Import the ResponseEntity class
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.getarrays.fileuploadanddownload.model.File;
import io.getarrays.fileuploadanddownload.service.FileService;

import java.io.FileNotFoundException; 
import java.io.IOException; 

@RestController
public class FileController {

    @Autowired
    private FileService fileService;
    @PostMapping("/files")
    public File uploadFile(@RequestParam("files") MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        byte[] data = multipartFile.getBytes();
        return fileService.uploadFile(filename, contentType, data);
        
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) throws IOException {
        File file = fileService.downloadFile(fileId);
        if (file != null) {
            HttpHeaders headers = new HttpHeaders();
            //headers.add("File-Id",fileId);
            headers.setContentType(MediaType.parseMediaType(file.getContentType()));
            headers.setContentDisposition(ContentDisposition.attachment().filename(file.getFilename()).build());
            return new ResponseEntity<>(file.getData(), headers, HttpStatus.OK);
        } else {
            throw new FileNotFoundException("File not found with ID: " + fileId);
        }
    }
}