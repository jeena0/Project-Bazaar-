package com.asiya.projectbazar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.asiya.projectbazar.entity.User;
import com.asiya.projectbazar.service.UserService;

import jakarta.servlet.http.HttpServlet;

@Controller
public class AdminController {
	@Autowired
	private final UserService userService;
	
	public AdminController (UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/admin/home")
	public String adminHome(Principal p, Model model) {
		model.addAttribute("user",p.getName());
		return"admin/index";
	}
	
	@GetMapping("/admin/user/show")
	public String user(Principal p, Model model, String username) {
		model.addAttribute("user_list",userService.getAllUser());
		return "admin/approval";
	}
	
	@GetMapping("/admin/profile")
	public String adminProfile(Principal principal, Model model) {
	 
	    return "admin/profile";
	}

		

}
