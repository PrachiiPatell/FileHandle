package com.example.file.controller;

import org.springframework.web.multipart.MultipartFile;
import com.example.file.model.FileUploadHelper;
import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {
//	public static final String DIRECTORY =System.getProperty("user.home") + "/Downloads/uploads/"; 
//	@PostMapping("/upload")
//	public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles){
//		List<String> filenames=new ArrayList<>();
//		for(MultipartFile file:multipartFiles) {
//				String filename=StringUtils.cleanPath(file.getOriginalFilename());
//				Path fileStorage=get(DIRECTORY,filename).toAbsolutePath().normalize();
//				copy(file.getInputStream(),fileStorage,REPLACE_EXISTING);
//		}
//		return ResponseEntity.ok().body(filenames);
//	}
// @Value("${file.upload.directory}")
//	  private String fileUploadDirectory;
//	 @PostMapping("/upload")
//	  public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//	    // You can access the file using the "file" parameter
//	    // For example, you can get the file's original name and contents like this:
//    String originalFilename = file.getOriginalFilename();
//    byte[] fileBytes = file.getBytes();
//	    String destinationPath = fileUploadDirectory + "/" + file.getOriginalFilename();
//
//	    // Save the uploaded file to the destination path
//	    file.transferTo(new File(destinationPath));
//
//    // Add your logic to process the uploaded file here
//
//    // Return a response to the front-end
//    return "File uploaded successfully!";
//	  }
	@Autowired
	private FileUploadHelper fileUploadHelper;
@PostMapping("/upload")
public ResponseEntity<String> uploadFile(@RequestParam ("file") MultipartFile file){
	System.out.println(file.getOriginalFilename());
	System.out.println(file.getName());
	System.out.println(file.getContentType());
	System.out.println(file.getSize());
	
	try {
		
	if (file.isEmpty()) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request Must Contain File");
	}
	
	boolean f=fileUploadHelper.uploadFile(file);
	if(f) {
	
	return ResponseEntity.ok("File is Successfully Uploaded");
	}
	
}
	catch(Exception e) {
		e.printStackTrace();
	}
	
		
	
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
	}


//private Path get(String directory2, String filename) {
//		// TODO Auto-generated method stub
//		return null;
//	}


//@GetMapping("download/{filename}")
//	public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename){
//	
//}











  }                                                                                                                                                                                                             
