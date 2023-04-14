package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.FileModel;
import com.example.demo.repo.filerepo;

@RestController
@RequestMapping("/files")
@CrossOrigin("*")
public class Fileupload {
	
	public static final String dir = "C:\\Users\\PrachiPatel\\eclipse-workspace\\FileUploadingdemo-1\\src\\main\\resources\\static\\Files\\";
	
	@Autowired
	filerepo frepo;
	
	@PostMapping("/postfile")
	public ResponseEntity<?> postfile(@RequestParam("file") MultipartFile multipartFile, FileModel file) throws IOException{
		
		String filename="";
		
		filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Path fileStorage = Paths.get(dir, filename).toAbsolutePath().normalize();
		Files.copy(multipartFile.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
		
		file.setName(multipartFile.getOriginalFilename());
	
		
		frepo.save(file);
		return ResponseEntity.ok(file);
	}
	
	/*
	 * @GetMapping("/listfiles") public ResponseEntity<List<String>> listFiles()
	 * throws IOException { List<String> filenames = Files.list(Paths.get(dir))
	 * .map(path -> path.getFileName().toString()) .collect(Collectors.toList());
	 * return ResponseEntity.ok(filenames); }
	 */
	@GetMapping("/listfiles")
	public ResponseEntity<List<Map<String, String>>> listFiles() {
	    List<FileModel> files = frepo.findAll();
	    List<Map<String, String>> fileData = new ArrayList<>();
	    for (FileModel file : files) {
	        Map<String, String> fileMap = new HashMap<>();
	        fileMap.put("id", file.getId().toString());
	        fileMap.put("name", file.getName());
	        fileData.add(fileMap);
	    }
	    return ResponseEntity.ok(fileData);
	}
	
	@GetMapping("/deletefile/{id}")
	public ResponseEntity<?> deleteFile(@PathVariable("id") String id) {
	    Optional<FileModel> fileOptional = frepo.findById(id);
	    if (fileOptional.isPresent()) {
	        FileModel file = fileOptional.get();
	        frepo.deleteById(id);
	        Path filePath = Paths.get(dir, file.getName()).toAbsolutePath().normalize();
	        try {
	            Files.delete(filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	        return ResponseEntity.ok().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/downloadfile/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("id") String id) throws IOException {
	    Optional<FileModel> fileOptional = frepo.findById(id);
	    if (fileOptional.isPresent()) {
	        FileModel file = fileOptional.get();
	        Path filePath = Paths.get(dir, file.getName()).toAbsolutePath().normalize();
	        Resource resource = new UrlResource(filePath.toUri());
	        if (resource.exists()) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
	            return ResponseEntity.ok()
	                    .headers(headers)
	                    .contentLength(resource.contentLength())
	                    .contentType(MediaType.parseMediaType("application/octet-stream"))
	                    .body(resource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}




}
