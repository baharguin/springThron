package com.example.spring.controller;

import com.example.spring.dao.ClientRepo;
import com.example.spring.dao.ExcelRepo;
import com.example.spring.model.Client;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
public class FileUpload {
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	ExcelRepo excelRepo;
	
	@RequestMapping("/")
	public String addClient(Client client)
	{
	//	client.setName("bahar");
	//	clientRepo.save(client);
		return client.toString();
	}
	
	@RequestMapping("/show")
	public Iterable<Client> getClient(Client client) throws IOException {
		excelRepo.read();
		return clientRepo.findAll();
	}
	
	@RequestMapping("/import")
	public void mapReapExcelDatatoDB() throws IOException {
		
		String excelFilePath = "C:/Users/bakhtar/spring/target/test.xls";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet worksheet = workbook.getSheetAt(0);
		
		for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
			Client client = new Client();
			Row row = worksheet.getRow(i);
			DataFormatter formatter = new DataFormatter();
			Cell cell = row.getCell(0);
			String phoneNumber = formatter.formatCellValue(cell); //Returns the 
			// formatted value of a cell as a String regardless of the cell type.
			if(clientRepo.findByPhoneNumber(phoneNumber) == null) {
				String fullName =
						row.getCell(1).getStringCellValue() + row.getCell(2).getStringCellValue();
				client.setName(fullName);
				client.setPhoneNumber(phoneNumber);
				clientRepo.save(client);
			}
		}
		
	}
}