package com.example.fileUploadDownloadDemo.service;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.example.fileUploadDownloadDemo.entity.Employee;
import com.example.fileUploadDownloadDemo.entity.Header;
import com.example.fileUploadDownloadDemo.exception.FileWriteException;
import com.example.fileUploadDownloadDemo.repository.FileDownloadRepository;

@Service
public class FileDownloadService {

	private static final Logger LOG = LoggerFactory.getLogger(FileDownloadService.class);
	private static final String EMPLOYEE_CSV = "Employee.csv";
	private static final String TEXT_CSV = "text/csv";

	@Autowired
	private FileDownloadRepository fileDownloadRepository;

	public void downloadCSVFile(HttpServletResponse response) {
		try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT.withHeader(Header.getHeaders()));) {
			response.setContentType(TEXT_CSV);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + EMPLOYEE_CSV + "\"");
			for (Employee employee : fileDownloadRepository.findAll()) {
				csvPrinter.printRecord(getListOfColumnValues(employee));
			}
			LOG.info("Successfully written data into :: {}", EMPLOYEE_CSV);
		} catch (Exception e) {
			LOG.error("Error occured while wrting data into csv file", e);
			throw new FileWriteException(e.getMessage());
		}
	}

	private List<String> getListOfColumnValues(Employee employee) {
		return Arrays.asList(employee.getId(), employee.getFirstName(), employee.getMiddleName(),
				employee.getLastName(), employee.getGender(), employee.getDateOfBirth());
	}
	
}