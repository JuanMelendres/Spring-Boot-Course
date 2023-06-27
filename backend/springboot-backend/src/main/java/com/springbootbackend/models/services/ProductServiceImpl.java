package com.springbootbackend.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootbackend.models.dao.IProductDao;
import com.springbootbackend.models.entity.Product;

@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private IProductDao productDao;

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productDao.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return (List<Product>) productDao.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public List<Product> findProductByName(String term) {
		return productDao.findByproductNameContainingIgnoreCase(term);
	}

}
