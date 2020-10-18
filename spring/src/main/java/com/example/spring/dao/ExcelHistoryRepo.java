package com.example.spring.dao;

import com.example.spring.model.ExcelHistory;
import org.springframework.data.repository.CrudRepository;

public interface ExcelHistoryRepo extends CrudRepository<ExcelHistory, Integer>{
	
	public ExcelHistory findByFileName(String fileName);
	
}