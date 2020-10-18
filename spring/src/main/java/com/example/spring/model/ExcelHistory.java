package com.example.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.nio.file.Path;

@Entity
public class ExcelHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int failedRate;
	
	private int successRate;
	
	private int totalRecord;
	
	private String sheetName;
	
	private String fileName;
	
	private BigDecimal fileStatus;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getFailedRate() {
		return failedRate;
	}
	
	public void setFailedRate(int failedRate) {
		this.failedRate = failedRate;
	}
	
	public int getSuccessRate() {
		return successRate;
	}
	
	public void setSuccessRate(int successRate) {
		this.successRate = successRate;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public BigDecimal getFileStatus() {
		return fileStatus;
	}
	
	public void setFileStatus(BigDecimal fileStatus) {
		this.fileStatus = fileStatus;
	}
	
	public int getTotalRecord() {
		return totalRecord;
	}
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
}