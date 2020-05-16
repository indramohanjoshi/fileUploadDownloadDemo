package com.example.fileUploadDownloadDemo.repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileUploadDownloadDemo.entity.Employee;
import com.example.fileUploadDownloadDemo.exception.IncorrectFileException;
import com.example.fileUploadDownloadDemo.service.FileUploadRepository;

@Service
public class FileUploadService {

	@Autowired
	private FileUploadRepository fileUploadRepository;

	@Async
	public String asyncUploadCSVFile(final MultipartFile file) throws IOException {
		return uploadCSVFile(file);
	}

	public String uploadCSVFile(final MultipartFile file) throws IOException {

		if (file.isEmpty()) {
			throw new IncorrectFileException("can not upload empty file.");
		}

		try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), "UTF-8");
				CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withFirstRecordAsHeader())) {
			// Instead of creating Object and setting it's properties, directly insert
			// scripts can be build
			List<Employee> employees = new ArrayList<>();
			List<String> headerNames = csvParser.getHeaderNames();
			csvParser.forEach(row -> {
				employees.add(new Employee(getEmployeeProperties(row, headerNames)));
				// for each chunk of list, DB calls can be made to reduce memory resource
				// utilization
			});
			// bulk insert - batch update can be used to minimized DB calls
			System.out.println("making db call");
			fileUploadRepository.saveAll(employees);
		}
		return "Request completed successfully!";
	}

	private List<String> getEmployeeProperties(CSVRecord row, List<String> columns) {
		List<String> properties = new ArrayList<>();
		columns.forEach(column -> {
			properties.add(getIndexedColumnValue(row, column));
		});
		return properties;
	}

	private String getIndexedColumnValue(CSVRecord row, String column) {
		return StringUtils.isNotBlank(row.get(column)) ? row.get(column) : StringUtils.EMPTY;
	}

}