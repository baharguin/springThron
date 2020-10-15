package com.example.spring.dao;

import org.springframework.stereotype.Repository;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface  ExcelRepo {
	
	public List<String> readExcelFile() throws IOException;
	
	public List<String> read() throws IOException;
	
}
