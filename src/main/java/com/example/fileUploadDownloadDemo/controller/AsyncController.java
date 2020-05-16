package com.example.fileUploadDownloadDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileUploadDownloadDemo.repository.FileUploadService;

@RestController
@RequestMapping("/asyncapi")
public class AsyncController {

	@Autowired
	private FileUploadService fileUploadService;

	@Value("${request.timeout:5000}")
	private int requestTimeOut;

	@PostMapping(value = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public WebAsyncTask<String> aSyncUploadCSVFile(@RequestParam("file") MultipartFile file) {
		System.out.println("service start...");
		WebAsyncTask<String> task = new WebAsyncTask<>(requestTimeOut, () -> {

			System.out.println("task execution start...");
			//WebAsyncTask is configured to timeout in 6 second as per application.proeprties file or 5 second default value. 
			//If Asynchronous method did not get called in 6 second then task's timeout callback method will be invoked.
			//In case of failure of Asynchronous method task's error method will be invoked.
			//In case of successful completion of Asynchronous method task's completion method will be invoked.
			//These methods can be used to do call back jobs for example updating file upload status in table.
			//Thread.sleep(7000);
			String result = fileUploadService.asyncUploadCSVFile(file);

			System.out.println("task execution end...");
			return result;
		});
		task.onTimeout(() -> {
			System.out.println("onTimeout...");
			return "Request timed out !";
		});
		task.onError(() -> {
			System.out.println("onError...");
			return "Some error occurred...";
		});
		task.onCompletion(() -> {
			System.out.println("onCompletion...");
		});
		System.out.println("service end...");
		return task;
	}
}
