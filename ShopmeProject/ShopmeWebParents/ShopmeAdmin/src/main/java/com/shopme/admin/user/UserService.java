package com.shopme.admin.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.commons.entity.Role;
import com.shopme.commons.entity.User;

@Service
public class UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public List<User> getUsers() {
		return (List<User>) this.userRepository.findAll();
	}
	
	public List<Role> getRoles() {
		return (List<Role>) this.roleRepository.findAll();
	}

	public void save(User user) {
		this.encodePassword(user);
		this.userRepository.save(user);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
}
