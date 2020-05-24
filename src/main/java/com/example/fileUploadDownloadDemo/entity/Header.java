package com.example.fileUploadDownloadDemo.entity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Header {
	ID("EmployeeId"), FIRSTNAME("First_Name"), MIDDLENAME("Middle_Name"), LASTNAME("Last_Name"), GENDER("Gender"),
	DATEOFBIRTH("Date_Of_Birth");

	private String value;

	Header(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static final String[] getHeaders() {
		return Stream.of(Header.values()).map(Header::getValue).collect(Collectors.toList()).toArray(new String[0]);
	}
}
