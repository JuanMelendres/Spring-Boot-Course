package com.springbootbackend.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootbackend.models.dao.IUsersDao;
import com.springbootbackend.models.entity.Users;

@Service
public class UsersService implements IUsersService, UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UsersService.class);

	@Autowired
	private IUsersDao userDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users users = userDao.findByuserName(username);
		
		if(users == null) {
			logger.error("Login error: user not found '" + username + "' in the system!");
			throw new UsernameNotFoundException("Login error: user not found '" + username + "' in the system!");
		}
		
		List<GrantedAuthority> authorities = users.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		logger.info("Role: " + authorities);
		
		User user = new User(users.getFirstName(), users.getPassword(), users.getEnabled(), true, true, true, authorities);
		
		logger.info("user: " + user.toString());
		
		return user;
	}

	@Override
	@Transactional(readOnly=true)
	public Users findByuserName(String userName) {
		return userDao.findByuserName(userName);
	}

}




