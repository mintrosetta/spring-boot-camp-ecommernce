package com.shopme.admin.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.commons.entity.Role;
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
		List<User> users = this.userService.getUsers();

		model.addAttribute("users", users);
		
		return "users/index";
	}
	
	@GetMapping("create")
	public String viewCreateUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		List<Role> roles = this.userService.getRoles();
		
		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		
		return "users/create";
	}
	
	@PostMapping("create")
	public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		System.out.println(user.toString());
		this.userService.save(user);
		
		// use for past data to redirect url
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/users";
	}
}
