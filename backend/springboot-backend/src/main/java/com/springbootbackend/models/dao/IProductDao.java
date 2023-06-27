package com.springbootbackend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.springbootbackend.models.entity.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

	@Query("select p from Product p where p.productName like %?1%")
	public List<Product> findByproductName(String term);
	
	public List<Product> findByproductNameContainingIgnoreCase(String term);
	
	public List<Product> findByproductNameStartingWithIgnoreCase(String term);
}
