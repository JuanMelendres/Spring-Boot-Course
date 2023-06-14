package com.springbootbackend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springbootbackend.models.entity.Users;

public interface IUsersDao extends CrudRepository<Users, Long> {
	
	public Users findByuserName(String username);
	
	@Query("select u from Users u where u.userName=?1")
	public Users findByuserName2(String username);

}
