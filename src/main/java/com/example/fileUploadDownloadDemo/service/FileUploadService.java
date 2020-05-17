package com.example.fileUploadDownloadDemo.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileUploadDownloadDemo.entity.Employee;
import com.example.fileUploadDownloadDemo.entity.UploadFileRequest;
import com.example.fileUploadDownloadDemo.exception.IncorrectFileException;
import com.example.fileUploadDownloadDemo.repository.FileUploadRepository;
import com.example.fileUploadDownloadDemo.repository.UploadFileRequestRepository;

@Service
public class FileUploadService {

	private static final Logger LOG = LoggerFactory.getLogger(FileUploadService.class);

	private static final String USER = "John";
	private static final String PROCESSING = "Processing";
	private static final String TO_BE_PROCESS = "ToBeProcess";
	private static final String SUCCESS = "Success";
	private static final String FAILED = "Failed";

	@Autowired
	private FileUploadRepository fileUploadRepository;

	@Autowired
	private UploadFileRequestRepository uploadFileRequestRepository;

	public String uploadCSVFileRequest(final MultipartFile file) {

		if (file.isEmpty()) {
			throw new IncorrectFileException(file.getOriginalFilename(), "can not upload empty file.");
		}

		try {
			LOG.info("insert file upload request fileName :: {}", file.getOriginalFilename());
			UploadFileRequest uploadFileRequest = new UploadFileRequest();
			uploadFileRequest.setFileName(file.getOriginalFilename());
			uploadFileRequest.setFileSize(file.getSize());
			uploadFileRequest.setFileStatus(TO_BE_PROCESS);
			uploadFileRequest.setFileStatusDescription("File to be process..");
			uploadFileRequest.setFileUploadedBy(USER);
			uploadFileRequest.setFileLastModifiedBy(USER);
			uploadFileRequest.setFileUploadedAt(LocalDateTime.now());
			uploadFileRequest.setFileLastModifiedAt(LocalDateTime.now());
			uploadFileRequestRepository.save(uploadFileRequest);
		} catch (Exception ex) {
			throw new IncorrectFileException(file.getOriginalFilename(), ex.getMessage());
		}
		return "File Upload Request Submitted SuccessFully !";
	}

	public String uploadCSVFile(final MultipartFile file, boolean isSyncMode) throws IOException {

		if (file.isEmpty()) {
			throw new IncorrectFileException(file.getOriginalFilename(), "can not upload empty file.");
		}

		updateFileStatus(file.getOriginalFilename(), PROCESSING, "File is being proces..!");

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
			fileUploadRepository.saveAll(employees);
		} catch (Exception ex) {
			LOG.error("some error occured while processing!", ex);
			if (isSyncMode) {
				updateFileStatus(file.getOriginalFilename(), FAILED, ex.getMessage());
			}
			throw new IncorrectFileException(file.getOriginalFilename(), "some error occured while processing!");
		}
		if (isSyncMode) {
			updateFileStatus(file.getOriginalFilename(), SUCCESS, "File processed Successfully!");
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

	public void updateFileStatus(String fileName, String status, String description) {
		LOG.info("update file :: {} status :: {}", fileName, status);
		UploadFileRequest fileRequest = uploadFileRequestRepository.findByFileName(fileName);
		fileRequest.setFileStatus(status);
		fileRequest.setFileStatusDescription(description);
		fileRequest.setFileLastModifiedBy("System");
		fileRequest.setFileLastModifiedAt(LocalDateTime.now());
		uploadFileRequestRepository.save(fileRequest);
	}

}