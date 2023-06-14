package com.springbootbackend.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootbackend.models.entity.Region;

public interface IRegionDao extends JpaRepository<Region, Long> {

}
