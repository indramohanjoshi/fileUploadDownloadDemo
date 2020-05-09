package com.example.fileUploadDownloadDemo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileUploadDownloadDemo.repository.FileUploadService;

@RestController
@RequestMapping("/api")
public class FileUploadController {

	@Autowired
	FileUploadService fileUploadService;

	@PostMapping(value = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public boolean uploadCSVFile(@RequestParam("file") MultipartFile file) throws IOException {
		return fileUploadService.uploadCSVFile(file);
	}
}
