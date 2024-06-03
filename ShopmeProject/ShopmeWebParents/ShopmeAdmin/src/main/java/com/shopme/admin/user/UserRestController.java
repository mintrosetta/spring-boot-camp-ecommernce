package com.shopme.admin.user;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserRestController {
	
	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("check_email")
	public String checkDuplicateEmail(@Param("email") String email) {
		return this.userService.isEmailUnique(email) ? "OK" : "Duplicated";
	}
	
}
