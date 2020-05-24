package com.example.fileUploadDownloadDemo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fileUploadDownloadDemo.service.FileDownloadService;

@RestController
@RequestMapping("/api")
public class FileDownloadController {

	@Autowired
	private FileDownloadService fileDownloadService;

	@GetMapping(value = "/downloadfile")
	public void downloadCSV(HttpServletResponse httpServletResponse) {
		fileDownloadService.downloadCSVFile(httpServletResponse);
	}
}