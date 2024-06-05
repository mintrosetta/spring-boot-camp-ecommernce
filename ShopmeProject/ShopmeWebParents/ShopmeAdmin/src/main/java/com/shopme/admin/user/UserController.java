package com.shopme.admin.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
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
		return this.listByPage(1, model);
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
	public String createUser(
		@ModelAttribute("user") User user, 
		RedirectAttributes redirectAttributes,
		@RequestParam("image") MultipartFile multipartFile) {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String uploadDir = "user-photos";

			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	
			user.setPhotos(fileName);
		} else {
			user.setPhotos(null);
		}
		
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
	
	@GetMapping("enabled/{userId}/false")
	public String disableUserById(@PathVariable("userId") Integer userId, Model model) {
		this.userService.updateUSerEnabledStatus(userId, false);
		return "redirect:/users";
	}
	
	@GetMapping("enabled/{userId}/true")
	public String enableUserById(@PathVariable("userId") Integer userId, Model model) {
		this.userService.updateUSerEnabledStatus(userId, true);
		return "redirect:/users";
	}

	@GetMapping("page/{pageNumber}")
	public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model) {
		Page<User> usersPage = this.userService.listByPage(pageNumber);

		long startCount = (pageNumber - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;

		if (endCount > usersPage.getTotalElements()) {
			endCount = usersPage.getTotalElements();
		}

		model.addAttribute("totalPage", usersPage.getTotalPages());
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", usersPage.getContent().size());
		model.addAttribute("users", usersPage.getContent());

		return "users/index";
	}
}
