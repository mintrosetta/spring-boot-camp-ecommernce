package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test 
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = "12345";
		String passwordHash = passwordEncoder.encode(password);
		
		System.out.println(passwordHash);
		
		boolean matches = passwordEncoder.matches(password, passwordHash);
		assertThat(matches).isTrue();
	}
	
}
