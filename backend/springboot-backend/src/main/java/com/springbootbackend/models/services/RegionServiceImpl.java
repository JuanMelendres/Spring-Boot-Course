package com.springbootbackend.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootbackend.models.dao.IRegionDao;
import com.springbootbackend.models.entity.Region;

@Service
public class RegionServiceImpl implements IRegionService {
	
	@Autowired
	private IRegionDao regionDao;

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAll() {
		return regionDao.findAll();
	}

}
