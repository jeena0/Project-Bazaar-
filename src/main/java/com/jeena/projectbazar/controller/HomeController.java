package com.asiya.projectbazar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;
import com.asiya.projectbazar.service.ProductService;
import com.asiya.projectbazar.service.UserService;

@Controller
public class HomeController {
	
	
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	
	@GetMapping({"/","index"})
	public String homepage() {
		return "dashboard";
	}
	

	  // the user will add the products and the products will be visible to all the users
	  //but the question is will the user be able to buy their own products 
	  //to allow the user to just view their products but cannot add to cart their own products
}
