package com.springbootbackend.models.services;

import com.springbootbackend.models.entity.Users;

public interface IUsersService {
	
	public Users findByuserName(String userName);
}
