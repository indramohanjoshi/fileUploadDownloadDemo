package com.example.fileUploadDownloadDemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.fileUploadDownloadDemo.exception.IncorrectFileException;
import com.example.fileUploadDownloadDemo.service.FileUploadService;

public class MyListenableFutureCallback implements ListenableFutureCallback<String> {
	
	private static final Logger LOG = LoggerFactory.getLogger(MyListenableFutureCallback.class);
	
	private static final String SUCCESS = "Success";
	private static final String FAILED = "Failed";

	@Autowired
	private FileUploadService fileUploadService;

	@Override
	public void onFailure(Throwable ex) {
		LOG.error("error occured ", ex);
		if (ex instanceof IncorrectFileException) {
			IncorrectFileException exception = (IncorrectFileException) ex;
			LOG.error("update file :: {} status to Failed. ", exception.getFileName());
			fileUploadService.updateFileStatus(exception.getFileName(), FAILED, ex.getMessage());
		}
	}

	@Override
	public void onSuccess(String fileName) {
		LOG.info("update file :: {} status to Success. ", fileName);
		fileUploadService.updateFileStatus(fileName, SUCCESS, "File processed successfully!");
	}
}