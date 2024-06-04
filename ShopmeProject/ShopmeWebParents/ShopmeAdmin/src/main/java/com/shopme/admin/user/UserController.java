package com.shopme.admin.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		model.addAttribute("pageTitle", "Create new user");
		
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
	
	@GetMapping("edit/{userId}")
	public String editUser(@PathVariable("userId") Integer userId, Model model, RedirectAttributes redirectAttribute) {
		try {
			Optional<User> user = this.userService.getUserById(userId);
			
			if (user.isPresent()) {
				List<Role> roles = this.userService.getRoles();
				
				model.addAttribute("user", user.get());
				model.addAttribute("pageTitle", "Edit user ID: " + userId);
				model.addAttribute("roles", roles);
			} else {
				throw new UserNotFoundException("User not found by ID: " + userId);
			}
			
			return "users/create";	
		} catch (Exception ex) {
			redirectAttribute.addFlashAttribute("message", ex.getMessage());
			
			return "redirect:/users";
		}
	}
	
	@GetMapping("delete/{userId}")
	public String deleteUser(@PathVariable("userId") Integer userId, Model model, RedirectAttributes redirectAttribute) {
		try {
			this.userService.delete(userId);
			
			redirectAttribute.addFlashAttribute("message", "The user ID " + userId + " has bean deleted successful.");
		} catch (Exception ex) {
			redirectAttribute.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/users";
	}
}
