package com.shopme.admin.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopme.commons.entity.User;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> listAll() {
		return (List<User>) this.userRepository.findAll();
	}
}
