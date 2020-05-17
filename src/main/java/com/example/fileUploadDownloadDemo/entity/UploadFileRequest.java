package com.example.fileUploadDownloadDemo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "uploadFileRequest")
public class UploadFileRequest {
	@Id
	private String id;
	@Indexed(unique=true, sparse=true)
	private String fileName;
	private long fileSize;
	private String fileStatus;
	private String fileStatusDescription;
	private String fileUploadedBy;
	private LocalDateTime fileUploadedAt;
	private String fileLastModifiedBy;
	private LocalDateTime fileLastModifiedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getFileStatusDescription() {
		return fileStatusDescription;
	}

	public void setFileStatusDescription(String fileStatusDescription) {
		this.fileStatusDescription = fileStatusDescription;
	}

	public String getFileUploadedBy() {
		return fileUploadedBy;
	}

	public void setFileUploadedBy(String fileUploadedBy) {
		this.fileUploadedBy = fileUploadedBy;
	}

	public LocalDateTime getFileUploadedAt() {
		return fileUploadedAt;
	}

	public void setFileUploadedAt(LocalDateTime fileUploadedAt) {
		this.fileUploadedAt = fileUploadedAt;
	}

	public String getFileLastModifiedBy() {
		return fileLastModifiedBy;
	}

	public void setFileLastModifiedBy(String fileLastModifiedBy) {
		this.fileLastModifiedBy = fileLastModifiedBy;
	}

	public LocalDateTime getFileLastModifiedAt() {
		return fileLastModifiedAt;
	}

	public void setFileLastModifiedAt(LocalDateTime fileLastModifiedAt) {
		this.fileLastModifiedAt = fileLastModifiedAt;
	}

}