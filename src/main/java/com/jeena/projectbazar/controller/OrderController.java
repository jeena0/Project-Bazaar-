package com.asiya.projectbazar.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asiya.projectbazar.entity.Cart;
import com.asiya.projectbazar.entity.OrderProduct;
import com.asiya.projectbazar.entity.OrderedProducts;
import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;
import com.asiya.projectbazar.service.CartService;
import com.asiya.projectbazar.service.OrderService;
import com.asiya.projectbazar.service.ProductService;
import com.asiya.projectbazar.service.UserService;

@Controller
public class OrderController {
	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@GetMapping("/user/payment/success/{agent}")
	public String order(@PathVariable String agent, Principal p, Model m,RedirectAttributes redirectAttributes) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
		} else {
			return "redirect:/login";
		}

		User user = userService.getUserByUsername(authentication.getName());
		if (user == null) {
		}

		// Proceed with order processing if user exists
		System.out.println("Agent: " + agent);
		// to check whetehr the cart is empty or not and redirecting accordidngly
		List<Cart> carts = cartService.getCartByUser(user);
		if (carts == null || carts.isEmpty()) {
			System.out.println("Empty cart");
			return "redirect:/user/cart/show"+ user.getId();
		}

		OrderProduct order = new OrderProduct();
		// Initialize order and process the cart items
		order.setUser(user);
		List<OrderedProducts> orderList = new ArrayList<>();
		double totalAmount = 0;

		for (Cart cart : carts) {
			totalAmount += cart.getProduct().getPrice() * cart.getQuantity();
			OrderedProducts orp = new OrderedProducts();
			orp.setProduct(cart.getProduct());
			orp.setQuantity(cart.getQuantity());
			orp.setOrderProduct(order);
			Product product = cart.getProduct();
			product.setQuantity(product.getQuantity() - cart.getQuantity());
			productService.updateProduct(product);
			orderList.add(orp);
			cartService.deleteCart(cart);
		}

		// Set order details
		order.setOrderedProducts(orderList);
		order.setTotal_amount(totalAmount - 100);
		order.setDiscount(100);
		order.setOrder_date(LocalDate.now());
		order.setDelivery_date(LocalDate.now().plusDays(7)); // Example delivery date
		order.setPayment_method(agent);
		order.setStatus("Status");

		// Save the order
		OrderProduct orpr = orderService.saveOrder(order);
		if (orpr != null) {
			redirectAttributes.addFlashAttribute("orderSuccess","Product Orderd Successfully");
		} else {
			System.out.println("Error while ordering the products");
			return "redirect:/user/cart/show"+ user.getId();
		}

		return "redirect:/myorders/"+user.getId(); 

	}

//show the product which the logged in user has ordered
	@GetMapping("/myorders/{id}")
	public String myorders(@PathVariable int id, Model m) {
		// Fetch the user by ID
		User user = userService.getUserById(id);

		// Get all orders associated with the user
		List<OrderProduct> ordersList = orderService.getOrderbyUser(user);

		// Add the list directly to the model
		m.addAttribute("orders", ordersList);

		return "user/ordered_products"; // JSP page to render
	}

	
	// To show the products which has been ordered by other users
	@GetMapping("/otherOrders/{id}")
	public String getOrdersByOwner(@PathVariable int id, Model model,RedirectAttributes redirectAttributes) {
        User productOwner = new User();
        productOwner.setId(id); // Assuming User entity has setId
        List<OrderedProducts> orders = orderService.getMyOrdersByUser(productOwner);
        model.addAttribute("orders", orders);
        redirectAttributes.addFlashAttribute("newOrder", "New order has been placed");
    

	    return "user/Orders"; // Renders "Orders.jsp"
	}



}
