package com.springbootbackend.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springbootbackend.models.entity.Bill;

public interface IBillDao extends CrudRepository<Bill, Long>{

}
