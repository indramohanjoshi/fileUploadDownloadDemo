package com.example.fileUploadDownloadDemo.exception;

public class IncorrectFileException extends RuntimeException {

	private static final long serialVersionUID = -5372340977043933411L;
	
	private String fileName;

	public IncorrectFileException(String fileName, String errorMessage) {
		super(errorMessage);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}