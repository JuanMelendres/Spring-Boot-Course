package com.springbootbackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootbackend.models.entity.Bill;
import com.springbootbackend.models.services.IBillService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bills", description = "Bills management APIs")
@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class BillRestController {
	
	@Autowired
	private IBillService billService;
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/bill/{id}")
	public ResponseEntity<?> getBillById(@PathVariable Long id) {

		Bill bill = null;

		Map<String, Object> response = new HashMap<>();

		try {

			bill = billService.findById(id);

		} catch (DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (bill == null) {
			response.put("message", "The Bill ID: ".concat(id.toString().concat(" is not found in the Data Base")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Bill>(bill, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/bill")
	public ResponseEntity<?> createClient(@Valid @RequestBody Bill bill, BindingResult result) {

		Bill newBill = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "The input '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			newBill = billService.save(bill);

		} catch (DataAccessException e) {
			response.put("message", "Error creating client in database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "The Bill has been created successfully");
		response.put("Bill", newBill);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/bill/{id}")
	public ResponseEntity<?> deleteBill(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {

			Bill bill = billService.findById(id);

			if (bill != null) {

				billService.delete(id);
				
				
			} else {
				response.put("message",
						"The Bill ID: ".concat(id.toString().concat(" is not found in the Data Base")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (DataAccessException e) {
			response.put("message", "Error deleting client in database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "The Bill has been Deleted successfully");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
