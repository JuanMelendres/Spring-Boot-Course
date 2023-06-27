package com.springbootbackend.models.services;

import java.util.List;

import com.springbootbackend.models.entity.Product;

public interface IProductService {
	
	public Product findById(Long id);
	
	public List<Product> findAll();
	
	public List<Product> findProductByName(String term);

}
