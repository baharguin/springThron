package com.example.spring.controller;

import com.example.spring.dao.ClientRepo;
import com.example.spring.dao.ExcelHistoryRepo;
import com.example.spring.dao.ExcelRepo;
import com.example.spring.model.Client;
import com.example.spring.model.ExcelHistory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.math.*;
import java.lang.*;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

@RestController
public class FileUpload {
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	ExcelRepo excelRepo;
	
	@Autowired
	ExcelHistoryRepo excelHistoryRepo;
	
	@RequestMapping("/show/fileName/{fileName}")
	public @ResponseBody ExcelHistory getExcelHistory(@PathVariable String fileName) throws IOException {
		System.out.println(fileName);
		return excelHistoryRepo.findByFileName(fileName);
	}
	
	@RequestMapping("/show")
	public Iterable<ExcelHistory> getExcelHistory() throws IOException {
		return excelHistoryRepo.findAll();
	}
	
	@RequestMapping("/import")
	public void mapReapExcelDatatoDB() throws IOException {
		
		String excelFilePath = "C:/Users/bakhtar/spring/target/test.xls";
		FileInputStream inputStream = new FileInputStream(
				new File(excelFilePath));
		// create object of Path 
		Path path = Paths.get("C:/Users/bakhtar/spring/target/test.xls");
		
		// call getFileName() and get FileName path object 
		Path fileName = path.getFileName();
		
		// print FileName 
		System.out.println("FileName: "
				+ fileName.toString());
		Workbook workbook = new XSSFWorkbook(inputStream);
		String sheetName = workbook.getSheetName(0);
		Sheet worksheet = workbook.getSheetAt(0);
		int numberOfFailedRows = 0;
		int numberOfSavedRows = 0;
		int totalNumberOfRows = worksheet.getPhysicalNumberOfRows();
		ExcelHistory excelHistory = new ExcelHistory();
		double fileStatus = 100; // it means all of them failes 100% succeeded
		excelHistory.setFileStatus(new BigDecimal(fileStatus));
		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			Client client = new Client();
			Row row = worksheet.getRow(i);
			DataFormatter formatter = new DataFormatter();
			Cell cell = row.getCell(0);
			String phoneNumber = formatter.formatCellValue(cell); //Returns the 
			// formatted value of a cell as a String regardless of the cell type.
			if (clientRepo.findByPhoneNumber(phoneNumber) == null) {
				String fullName =
						row.getCell(1).getStringCellValue() + row.getCell(
								2).getStringCellValue();
				client.setName(fullName);
				client.setPhoneNumber(phoneNumber);
				clientRepo.save(client);
				numberOfSavedRows++;
				
			} else {
				numberOfFailedRows++;
				excelHistory.setFileStatus(countFileStatus(new BigDecimal(worksheet
				 .getPhysicalNumberOfRows()), new BigDecimal(numberOfFailedRows)));
			}
		}
			excelHistory.setFailedRate(numberOfFailedRows);
			excelHistory.setSuccessRate(numberOfSavedRows);
			excelHistory.setFileName(fileName.toString());
			excelHistory.setSheetName(sheetName);
			excelHistory.setTotalRecord(
					excelHistory.getFailedRate() + excelHistory.getSuccessRate());
			excelHistoryRepo.save(excelHistory);
		
	}
	
	
	private BigDecimal countFileStatus(BigDecimal physicalNumberOfRows, BigDecimal numberOfFailedRows) {
		if( physicalNumberOfRows.intValue() == 0){
			return BigDecimal.valueOf(0);
		}
		if(physicalNumberOfRows ==  numberOfFailedRows){
			return BigDecimal.valueOf(100);
		}
;
		return numberOfFailedRows.divide(physicalNumberOfRows, 3, RoundingMode.CEILING);
		
	}
}