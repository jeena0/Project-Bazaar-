package com.asiya.projectbazar.controller;

import java.security.Principal;

//import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asiya.projectbazar.entity.User;
import com.asiya.projectbazar.entity.UserRole;
import com.asiya.projectbazar.service.ProductService;
//import com.asiya.projectbazar.entity.UserRole;
import com.asiya.projectbazar.service.UserService;

import jakarta.validation.Valid;

@Controller
//@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/login")
	public String showLogin() {
		return"user/userlogin";
	}
	
	@GetMapping("/register")
	public String register( Model m) {
		m.addAttribute("user", new User());
		return"user/register";
	}
	@PostMapping("/register")
	public ModelAndView registerUser(@Valid @ModelAttribute User user, BindingResult result, Model m,Principal p) {
	    if (result.hasErrors()) {
	        ModelAndView modelAndView = new ModelAndView("user/register");
	        modelAndView.addObject("valid_errors", result.getAllErrors());
	        return modelAndView;
	    }

	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    UserRole userRole = new UserRole();
	    userRole.setRole("ROLE_USER");
	    user.setEnable("1");
	    userRole.setUser(user);
	    user.setUserRole(userRole);
	    userService.saveUser(user);
	    return new ModelAndView("redirect:/login");
	}

	
	
@GetMapping("user/home")
public String showHome(Principal p, Model m) {
	User user=userService.getUserByUsername(p.getName());
	m.addAttribute("user",user);
	return "user/home";
}
	

		
@GetMapping("/welcome")
public String welcome(Principal p, Model model) {
    if (p == null) {
        // Redirect to login page if Principal is null (unauthenticated)
        return "redirect:/login";
    }
    
    // Fetch the user based on the authenticated user's username
    User user = userService.getUserByUsername(p.getName());
    if (user == null) {
        // Handle case where the user is not found in the database
        return "error"; // or redirect to a suitable error page
    }

    // Check user role and redirect accordingly
    if (user.getUserRole().getRole().contains("ROLE_ADMIN")) {
        return "redirect:/admin/home";
    } else if (user.getUserRole().getRole().contains("ROLE_USER")) {
        return "redirect:/user/home";
    }

    // Default case, redirect to dashboard if no role match
    return "dashboard";
}

	
	@GetMapping("user/edit")
	public String editProfile(Principal p, Model model) {
		
//		String username=p.getName();
		User user=userService.getUserByUsername(p.getName());
		model.addAttribute("user",user);
		model.addAttribute("edit",true);
		
		return"user/profile";
		
	}
	@PostMapping("user/edit")
	public String saveupdateProfile(@ModelAttribute User user ) {
		User uu=userService.getUserById(user.getId());
		
		userService.updateUserProfile(user);
		return"redirect:/user/edit";
	}
	
	@GetMapping("/updatePassoword")
	public String changePassword() {
		return"user/profile";
	}
			
	@PostMapping("/updatePassword/{id}")
	public String passowordChange(@RequestParam String password,
									@RequestParam String NewPassword,
									@RequestParam String ConfirmPassword, 
									Principal p, Model m, RedirectAttributes redirectAttributes) {
		
		if(NewPassword==(password)) {
			m.addAttribute("error","New password cannot be same as old");
			return" redirect:/user/edit";
		}
		
		if(!NewPassword.equals(ConfirmPassword)) {
			m.addAttribute("error","New passoword doesnot match the new password");
			return" redirect:/user/edit";
		}
		userService.changePassword(p.getName(),password,NewPassword);
		m.addAttribute("message", "Password Updated Successfully");
        return "redirect:/user/home";	
		
	}
	@PostMapping("/delete/{id}")
	public String deleteuser(@PathVariable int id, Principal p, Model m) {
		User user=userService.getUserById(id);
		if(user!=null) {
		userService.deleteUser(user);
		
		}
		return"redirect:/login";
	}
		
	}
	


