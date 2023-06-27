package com.springbootbackend.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadfileService {

	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

	private final static String DIR_UPLOAD = "uploads";

	@Override
	public Resource viewClientImage(String fileName) throws MalformedURLException {

		Path filePath = getPath(fileName);
		log.info(filePath.toString());

		Resource resource = null;

		resource = new UrlResource(filePath.toUri());

		if (!resource.exists() && !resource.isReadable()) {

			filePath = Paths.get("src/main/resources/static/images").resolve("no-user-found.png").toAbsolutePath();

			resource = new UrlResource(filePath.toUri());

			log.error("Error image could not be loaded: " + fileName);
		}

		return resource;
	}

	@Override
	public String copyImage(MultipartFile file) throws IOException {

		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
		
		Path filePath = getPath(fileName);
		
		log.info(filePath.toString());

		Files.copy(file.getInputStream(), filePath);
		
		log.info(fileName);

		return fileName;
	}

	@Override
	public boolean deleteImage(String fileName) {
		
		if (fileName != null && fileName.length() > 0) {
			
			log.info("Hi");
			
			Path lastFilePath = getPath(fileName);
			
			File lastPicturefile = lastFilePath.toFile();
			
			if (lastPicturefile.exists() && lastPicturefile.canRead()) {
				lastPicturefile.delete();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Path getPath(String fileName) {
		return Paths.get(DIR_UPLOAD).resolve(fileName).toAbsolutePath();
	}

}
