package com.example.fileUploadDownloadDemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.fileUploadDownloadDemo.entity.Employee;

public interface FileDownloadRepository extends MongoRepository<Employee, String> { }