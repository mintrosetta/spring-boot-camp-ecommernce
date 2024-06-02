package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.commons.entity.Role;

@DataJpaTest // use for test repository
@AutoConfigureTestDatabase(replace = Replace.NONE) // config type of database for test repository (ex. in-memory, database)
@Rollback(false) // after test, not roll back data
public class RoleRepositoryTests {
	
	RoleRepository roleRepository;
	
	@Autowired
	public RoleRepositoryTests(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Test
	public void testCreateAdminRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role savedRole = this.roleRepository.save(roleAdmin);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSalePersonRole() {
		Role roleSalePerson = new Role("SalePerson", "manage product price, customers, shipping, orders and sales report");
		Role savedRole = this.roleRepository.save(roleSalePerson);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateEditor() {
		Role roleEditor = new Role("Editor", "manage categories, brands, product, articles and menus");
		Role savedRole = this.roleRepository.save(roleEditor);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test 
	public void testCreateShipper() {
		Role roleShipper = new Role("Shipper", "view products, view orders, update order status");
		Role savedRole = this.roleRepository.save(roleShipper);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateAssistant() {
		Role roleAssistance = new Role("Assistant", "manage questions and reviews");
		Role savedRole = this.roleRepository.save(roleAssistance);
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
}
