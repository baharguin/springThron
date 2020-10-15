package com.example.spring.dao;

import com.example.spring.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepo extends CrudRepository<Client, Integer>{
	
	public Client findByPhoneNumber(String phoneNumber);
}