package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.commons.entity.Role;
import com.shopme.commons.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	public static final int USERS_PER_PAGE = 4;

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

	public Page<User> listByPage(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, USERS_PER_PAGE);

		return this.userRepository.findAll(pageable);
	}
	
	public List<Role> getRoles() {
		return (List<Role>) this.roleRepository.findAll();
	}

	public Integer save(User user) {
		boolean isUpdateUser = (user.getId() != null);
		if (isUpdateUser) {
			User existingUser = this.userRepository.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}
		} else {
			this.encodePassword(user);
		}
		
		User newUser = this.userRepository.save(user);
		return newUser.getId();
	}
	
	private void encodePassword(User user) {
		String encodedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(String email) {
		User user = this.userRepository.getUserByEmail(email);
		
		return user == null;
	}

	public Optional<User> getUserById(Integer userId) throws UserNotFoundException {
		try {
			return this.userRepository.findById(userId);
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID: " + userId);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long count = this.userRepository.countById(id);
		if (count == null || count == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		
		this.userRepository.deleteById(id);
	}
	
	@Transactional
	public void updateUSerEnabledStatus(Integer id, boolean enabled) {
		this.userRepository.updateEnabledStatus(id, enabled);
	}
}
