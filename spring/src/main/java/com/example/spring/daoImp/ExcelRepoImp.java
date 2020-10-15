package com.example.spring.daoImp;

import com.example.spring.dao.ExcelRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
class ExcelRepoImp implements ExcelRepo {
	
	public List<String> readExcelFile() throws IOException {
		FileInputStream file = new FileInputStream(new File("C:/Users/bakhtar" 
				+ "/spring/target/test.xls"));
		Workbook workbook = new XSSFWorkbook(file);
		
		Sheet sheet = workbook.getSheetAt(0);
		
		Map<Integer, List<String>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
			data.put(i, new ArrayList<String>());
			for (Cell cell : row) {
				System.out.println(row);

				}
			}
			i++;
		return null;
	}
	
	public List<String> read() throws IOException {
		String excelFilePath = "C:/Users/bakhtar/spring/target/test.xls";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue());
					break;
				}
				System.out.print(" - ");
			}
			System.out.println();
		}
		
		workbook.close();
		inputStream.close();
		return null;
	}
	
	}
	

