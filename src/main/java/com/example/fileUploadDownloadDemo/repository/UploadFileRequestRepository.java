package com.example.fileUploadDownloadDemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.fileUploadDownloadDemo.entity.UploadFileRequest;

public interface UploadFileRequestRepository extends MongoRepository<UploadFileRequest, String> {

	UploadFileRequest findByFileName(String fileName);

}