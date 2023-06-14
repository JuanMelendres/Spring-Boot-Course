package com.springbootbackend.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;

import com.springbootbackend.models.entity.Client;
import com.springbootbackend.models.entity.Region;
import com.springbootbackend.models.services.IClientService;
import com.springbootbackend.models.services.IRegionService;
import com.springbootbackend.models.services.IUploadfileService;

import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Clients", description = "Client management APIs")
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClientRestController {

	@Autowired
	private IClientService clientService;
	
	@Autowired
	private IUploadfileService uploadService;
	
	@Autowired
	private IRegionService regionService;
	
//	private final Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@GetMapping("/clients")
	public List<Client> getAllClients() {
		return clientService.findAll();
	}

	@GetMapping("/clients/page/{page}")
	public Page<Client> getAllClients(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return clientService.findAll(pageable);
	}

//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/client/{id}")
	public ResponseEntity<?> getClientById(@PathVariable Long id) {

		Client client = null;

		Map<String, Object> response = new HashMap<>();

		try {

			client = clientService.findById(id);

		} catch (DataAccessException e) {
			response.put("message", "Error when querying the database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (client == null) {
			response.put("message", "The Client ID: ".concat(id.toString().concat(" is not found in the Data Base")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/client")
	public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result) {

		Client newClient = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			/*
			 * List<String> errors = new ArrayList<>();
			 * 
			 * for (FieldError err: result.getFieldErrors()) { errors.add("The input '" +
			 * err.getField() + "' " + err.getDefaultMessage()); }
			 */

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "The input '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			newClient = clientService.save(client);

		} catch (DataAccessException e) {
			response.put("message", "Error creating client in database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "The client has been created successfully");
		response.put("Client", newClient);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/client/{id}")
	public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, BindingResult result,
			@PathVariable Long id) {

		Client currentClient = clientService.findById(id);

		Client updatedClient = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "The input '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (currentClient == null) {
			response.put("message", "Error, could not edit, the Client ID: "
					.concat(id.toString().concat(" is not found in the Data Base")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			currentClient.setLastName(client.getLastName());
			currentClient.setFirstName(client.getFirstName());
			currentClient.setEmail(client.getEmail());
			currentClient.setCreateAt(client.getCreateAt());
			currentClient.setRegion(client.getRegion());

			updatedClient = clientService.save(currentClient);

		} catch (DataAccessException e) {
			response.put("message", "Error updating client in database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "The client has been updated successfully");
		response.put("Client", updatedClient);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/client/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {

			Client client = clientService.findById(id);

			if (client != null) {
				String lastFileName = client.getProfilePicture();
				
				uploadService.deleteImage(lastFileName);

				clientService.delete(id);
			} else {
				response.put("message",
						"The Client ID: ".concat(id.toString().concat(" is not found in the Data Base")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (DataAccessException e) {
			response.put("message", "Error deleting client in database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "The client has been Deleted successfully");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_ADMIN"})
	@PostMapping("/client/upload")
	public ResponseEntity<?> uploadClientImage(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {

		Map<String, Object> response = new HashMap<>();

		Client client = clientService.findById(id);

		if (!file.isEmpty()) {
			
			String fileName = null;

			try {
				
				fileName = uploadService.copyImage(file);
				
			} catch (IOException e) {
				response.put("message", "Error uploading image: " + fileName);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String lastFileName = client.getProfilePicture();

			uploadService.deleteImage(lastFileName);

			client.setProfilePicture(fileName);

			clientService.save(client);

			response.put("client", client);
			response.put("message", "The image was uploaded successfully: " + fileName);
		} else {
			response.put("message", "Error with added image");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/client/upload/img/{fileName:.+}")
	public ResponseEntity<Resource> viewClientImage(@PathVariable String fileName) {

		Resource resource = null;

		try {
			
			resource = uploadService.viewClientImage(fileName);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/clients/regions")
	public List<Region> getAllRegions() {
		return regionService.findAll();
	}
}