package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.FileModel;

public interface filerepo extends MongoRepository<FileModel,String> {

}
