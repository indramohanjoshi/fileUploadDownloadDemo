package com.example.fileUploadDownloadDemo.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.fileUploadDownloadDemo.entity.Employee;

public interface FileUploadRepository extends MongoRepository<Employee, String> { }