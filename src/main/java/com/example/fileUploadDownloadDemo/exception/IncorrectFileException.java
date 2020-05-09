package com.example.fileUploadDownloadDemo.exception;

public class IncorrectFileException extends RuntimeException {

	private static final long serialVersionUID = -5372340977043933411L;

	public IncorrectFileException(String errorMessage) {
		super(errorMessage);
	}
}