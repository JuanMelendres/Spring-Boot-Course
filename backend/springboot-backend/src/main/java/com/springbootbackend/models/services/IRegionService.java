package com.springbootbackend.models.services;

import java.util.List;

import com.springbootbackend.models.entity.Region;


public interface IRegionService {
	
	public List<Region> findAll();

}
