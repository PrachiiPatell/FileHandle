package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
@Document(collection = "file")
public class FileModel {
	@Id
	private String id;
	private String Name;
	public FileModel() {
		super();
	}
	public FileModel(String name) {
		super();
		this.Name = name;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	
	

	
	
	

}
