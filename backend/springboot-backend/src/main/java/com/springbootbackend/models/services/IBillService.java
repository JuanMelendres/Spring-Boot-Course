package com.springbootbackend.models.services;

import com.springbootbackend.models.entity.Bill;

public interface IBillService {
	
	public Bill findById(Long id);
	
	public Bill save(Bill bill);
	
	public void delete(Long id);
}
