package com.asiya.projectbazar.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asiya.projectbazar.dao.OrderedProductDao;
import com.asiya.projectbazar.entity.Cart;
import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;
import com.asiya.projectbazar.service.CartService;
import com.asiya.projectbazar.service.OrderService;
import com.asiya.projectbazar.service.ProductService;
import com.asiya.projectbazar.service.UserService;


@Controller
@RequestMapping("/user/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderedProductDao orderedProductDao;

	
	
	@GetMapping("/add/{id}")
	public String addCart(@PathVariable int id, Model m,RedirectAttributes redirectAttributes) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";
	    }

	    String username = authentication.getName();
	    User user = userService.getUserByUsername(username);
	    Product product = productService.getProductById(id);

	    Optional<Cart> existingCart = cartService.getCartByUserAndProduct(user, product);

	    Cart cart;
	    if (existingCart.isPresent()) {
	        cart = existingCart.get();
	        cart.setQuantity(cart.getQuantity() + 1);
	    } else {
	        cart = new Cart();
	        cart.setProduct(product);
	        cart.setQuantity(1); // Start with 1
	        cart.setAddedDate(LocalDate.now());
	        cart.setUser(user);
	    }

	    cartService.saveCart(cart);
	    redirectAttributes.addFlashAttribute("Cartmessage","Added to cart");
	    return "redirect:/user/product/allproducts/"+ user.getId();
	}

	

	@GetMapping("/show/{id}")
	public String showCart(@PathVariable int id, Model m, Principal p) {
		
	    // Get the user by ID
	    User user = userService.getUserById(id);  
	    m.addAttribute("user", user);

	    // Get the cart items for the user
	    List<Cart> cartItems = cartService.getCartByUser(user);
	    boolean isCartOrdered = false;


	    // If any product has been ordered, clear the cart and show a message
	    if (isCartOrdered) {
	    	cartService.deleteUserCart(user);
//	        cartService.deleteUserCart(user);  // Delete all cart items for this user
	        m.addAttribute("message", "No Cart Left. All items have been ordered.");
	    } else {
	        // Otherwise, show the cart items
	        m.addAttribute("cartItems", cartItems);
	    }

	    // Return the cart view
	    return "user/cart"; 
	}
	
	
	

	@PostMapping("/update/{id}")
	public String updateCart(@PathVariable int id, @ModelAttribute Cart cart, RedirectAttributes redirectAttributes) {
	    // Retrieve the cart from the database by ID
		
			  
		    Optional<Cart> carts = cartService.getCartById(id);

	    if (carts.isPresent()) {
	        Cart existingCart = carts.get();

	        if (existingCart.getProduct() != null) {
	            int availableQuantity = existingCart.getProduct().getQuantity();  // Get available quantity of the product

	            if (cart.getQuantity() <= availableQuantity) {
	                existingCart.setQuantity(cart.getQuantity());
	                cartService.updateCart(existingCart);  
	                return "redirect:/user/cart/show/" +existingCart.getUser().getId();
	            } else {
//	                redirectAttributes.addAttribute("error", "quantityExceeded");
	            	redirectAttributes.addFlashAttribute("QuantityExceed","Added more carts then product");
	                return "redirect:/user/cart/show/" +existingCart.getUser().getId();
	            }
	        } else {
	        	redirectAttributes.addFlashAttribute("NullProduct","Quantity out of stock");
//	            redirectAttributes.addAttribute("error", "productNull");
                return "redirect:/user/cart/show/" +existingCart.getUser().getId();
	        }
	    } else {
	    	redirectAttributes.addFlashAttribute("noCartFound", "Cart Not Found");
//	        redirectAttributes.addAttribute("error", "cartNotFound");
	        return "redirect:/user/cart/show";  
	    }
	}
	
	//deleting a particular cart of the user
	@PostMapping("/delete/{id}")
	public String deleteCart(@PathVariable int id,RedirectAttributes redirectAttributes) {
	    Optional<Cart> carts = cartService.getCartById(id);  

	    if (carts.isPresent()) {  
	        Cart cart = carts.get();  
	        cartService.deleteCart(cart);  
	        redirectAttributes.addFlashAttribute("deleteOneSuccess","Deleted Successfully");
	        return "redirect:/user/cart/show/"+ cart.getUser().getId();
	    } else {
	    	redirectAttributes.addFlashAttribute("errordelete","Error deleting products");
	        return "redirect:/user/cart/show";
	    }
	}


//deleting all the carts of the particular user	
	@PostMapping("/deleteAll/{id}")
	public String deleteAllCart(@PathVariable("id") Integer id, Principal p,RedirectAttributes redirectAttributes) {
	    if (p == null) {
	        return "redirect:/login"; // Redirect to login if user is not authenticated
	    }

	    // Get user details
	    User user = userService.getUserById(id);

	    // Check if cart exists
	    List<Cart> carts = cartService.getCartByUser(user);
	    if (carts != null && !carts.isEmpty()) {  // Ensures the list is not null and has items
	        cartService.deleteUserCart(user);
	        redirectAttributes.addFlashAttribute("deleteSuccess"," All carts deleted Successfully");
	        return "redirect:/user/cart/show/"+ user.getId();
	    } else {
	    	redirectAttributes.addFlashAttribute("errordelete","Error deleting products");

	        return "redirect:/user/cart/show/"+user.getId();
	    }
	}

	



}
