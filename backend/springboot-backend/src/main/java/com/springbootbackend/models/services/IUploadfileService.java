package com.springbootbackend.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadfileService {
	
	public Resource viewClientImage(String fileName) throws MalformedURLException;
	
	public String copyImage(MultipartFile file) throws IOException;
	
	public boolean deleteImage(String fileName);
	
	public Path getPath(String fileName);

}
