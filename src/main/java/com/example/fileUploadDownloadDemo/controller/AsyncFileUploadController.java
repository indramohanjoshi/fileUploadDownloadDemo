package com.example.fileUploadDownloadDemo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileUploadDownloadDemo.service.FileUploadService;
import com.example.fileUploadDownloadDemo.service.AsyncFileUploadService;

@RestController
@RequestMapping("/asyncapi")
public class AsyncFileUploadController {

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private AsyncFileUploadService asyncFileUploadService;

	@PostMapping(value = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadCSVFileAsync(@RequestParam("file") MultipartFile file) throws IOException {
		String requestStatus = fileUploadService.uploadCSVFileRequest(file);
		asyncFileUploadService.uploadCSVFileAsync(file);
		return requestStatus;
	}
}
