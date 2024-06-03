package com.shopme.admin.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopme.commons.entity.User;

@Controller
@RequestMapping("users")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("")
	public String viewAllUser(Model model) {
		List<User> users = this.userService.listAll();
		
		model.addAttribute("users", users);
		
		return "users/index";
	}
}
