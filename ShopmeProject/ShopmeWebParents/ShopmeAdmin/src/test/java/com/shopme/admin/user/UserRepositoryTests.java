package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.commons.entity.Role;
import com.shopme.commons.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	// TestEntityManager create by Spring Data JPA for unit testing with repository
	@Autowired
	private TestEntityManager entityManager;
	
	@Test 
	public void testCreateUserWithOneRole() {
		Role adminRole = this.entityManager.find(Role.class, 2);
		
		User user = new User("admin01@gmail.com", "admin", "admin", "admin");
		user.addRole(adminRole);
		
		User savedUser = this.userRepository.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test 
	public void testCreateUserWithMultipleRole() {
		Role adminRole = this.entityManager.find(Role.class, 2);
		Role salePersonRole = this.entityManager.find(Role.class, 3);
		
		User user = new User("admin02@gmail.com", "admin02", "admin02", "admin02");
		user.addRole(adminRole);
		user.addRole(salePersonRole);
		
		User savedUser = this.userRepository.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> users = this.userRepository.findAll();
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test 
	public void testGetUserById() {
		Optional<User> user = this.userRepository.findById(1);
		
		assertThat(user.isPresent()).isTrue();
	}
	
	@Test
	public void testUpdateUser() {
		boolean enableValue = true;
		String emailValue = "change_test@gmail.com";
		
		User user = this.userRepository.findById(1).get();
		user.setEnabled(enableValue);
		user.setEmail(emailValue);
		
		this.userRepository.save(user);
	}
	
	@Test
	public void testUpdateUserRoles() {
		Role salePersonRole = this.entityManager.find(Role.class, 3);
		Role shipperRole = this.entityManager.find(Role.class, 5);
		
		User user = this.userRepository.findById(2).get();
		user.getRoles().remove(salePersonRole);
		user.addRole(shipperRole);
		
		this.userRepository.save(user);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		
		this.userRepository.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "mint.colorfuls@gmail.com";
		User user = this.userRepository.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
}
