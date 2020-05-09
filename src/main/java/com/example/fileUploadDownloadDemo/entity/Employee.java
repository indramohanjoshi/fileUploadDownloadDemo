package com.example.fileUploadDownloadDemo.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class Employee {
	@Id
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String dateOfBirth;

	public Employee() {

	}

	public Employee(List<String> proeprties) {
		super();
		this.id = proeprties.get(0);
		this.firstName = proeprties.get(1);
		this.middleName = proeprties.get(2);
		this.lastName = proeprties.get(3);
		this.gender = proeprties.get(4);
		this.dateOfBirth = proeprties.get(5);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}