package com.example.fileUploadDownloadDemo.exception;

public class FileWriteException extends RuntimeException {

	private static final long serialVersionUID = -5372340977043933411L;

	public FileWriteException(String errorMessage) {
		super(errorMessage);
	}

}