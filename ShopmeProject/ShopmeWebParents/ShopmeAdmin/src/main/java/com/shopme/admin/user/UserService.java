package com.shopme.admin.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopme.commons.entity.Role;
import com.shopme.commons.entity.User;

@Service
public class UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	public List<User> getUsers() {
		return (List<User>) this.userRepository.findAll();
	}
	
	public List<Role> getRoles() {
		return (List<Role>) this.roleRepository.findAll();
	}

	public void save(User user) {
		this.userRepository.save(user);
	}
}
