package com.springbootbackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springbootbackend.models.entity.Product;
import com.springbootbackend.models.services.IProductService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Products", description = "Products management APIs")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ProductRestController {
	
	@Autowired
	private IProductService productService;
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {

		Product product = null;

		Map<String, Object> response = new HashMap<>();

		try {

			product = productService.findById(id);

		} catch (DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (product == null) {
			response.put("message", "The product ID: ".concat(id.toString().concat(" is not found in the Data Base")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@GetMapping("/product")
	public List<Product> getAllProducts() {
		return productService.findAll();
	}
	
	@GetMapping("/product/filter-products/{term}")
	public List<Product> getFilteredProductByName(@PathVariable String term) {
		return productService.findProductByName(term);
	}

}
