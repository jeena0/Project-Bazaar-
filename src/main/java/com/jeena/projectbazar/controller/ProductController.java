package com.asiya.projectbazar.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer.RedirectionEndpointConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asiya.projectbazar.entity.Cart;

import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;
import com.asiya.projectbazar.service.CartService;
import com.asiya.projectbazar.service.CategoryService;
import com.asiya.projectbazar.service.FUploadService;
import com.asiya.projectbazar.service.OrderService;
import com.asiya.projectbazar.service.ProductService;
import com.asiya.projectbazar.service.UserService;

@Controller
@RequestMapping("/user/product")
public class ProductController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FUploadService fuploadService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;


	@GetMapping("/add")
	public String showAddProductPage(Principal principal, Model model) {
		// Ensure the user is authenticated
		String username = principal.getName(); //
		model.addAttribute("cat_list", categoryService.getAllCategories());
		model.addAttribute("user", username);
		model.addAttribute("product", new Product()); //
		return "product/product"; //
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/add")
	public String saveProduct(@ModelAttribute Product product,RedirectAttributes redirectAttributes) {
		// make sure that the auntheticated user is logged in
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

//fetching the user with the username
		User user = userService.getUserByUsername(username);
		if (user != null) {
			product.setUser(user);
		} else {
			System.out.println("User not found.");
			return "redirect:/user/product/add";
		}

		// Handling the image upload
		if (product.getImageFile() != null && !product.getImageFile().isEmpty()) {
			boolean isImageUploaded = fuploadService.uploadProductImage(product.getImageFile());
			if (isImageUploaded) {
				product.setImageName(product.getImageFile().getOriginalFilename());
				product.setAddedDate(LocalDate.now());
				productService.addProduct(product);
				redirectAttributes.addFlashAttribute("success","Product Added");
				// Save the product with the user_id
			    return "redirect:/user/product/view/"+ user.getId();
			} else {
				System.out.println("Error uploading product image for: " + product);
			}
		}

		return "redirect:/user/product/add"; // Redirect if image upload or product creation fails
	}
	
//for the product editing
	@GetMapping("/edit/{product_id}")
	public String editProduct( @PathVariable("product_id") int id, Model model, Principal p) {
		User user = userService.getUserByEmail(p.getName());
		model.addAttribute("user", user);
		Product product = productService.getProductById(id);
		model.addAttribute("edit", true);
//	model.addAttribute("product",product);
		model.addAttribute("product_list", productService.getAllProducts());
		model.addAttribute("edit_product", productService.getProductById(id));
		model.addAttribute("cat_list", categoryService.getAllCategories());
		return "product/product";
	}
	
	//updating the product
	@PostMapping("/update")
	public String updateProduct(@RequestParam("id") int id,
	        @ModelAttribute Product product,
	        @RequestParam(value = "pevImage", required = false) String pevImg, 
	        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile, 
	        Principal principal,
	        Model model) {

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	        return "redirect:/login";  // Redirect to login if user is not authenticated
	    }

	    String username = authentication.getName();
	    User user = userService.getUserByUsername(username);

	    Product existingProduct = productService.getProductById(id);
	    if (existingProduct == null || !existingProduct.getUser().equals(user)) {
	        model.addAttribute("error", "You are not authorized to update this product.");
	        return "error/403"; 
	    }

	    // Update product details
	    existingProduct.setName(product.getName());
	    existingProduct.setDescription(product.getDescription());
	    existingProduct.setPrice(product.getPrice());
	    existingProduct.setQuantity(product.getQuantity());
	    existingProduct.setCategory(product.getCategory());
	    existingProduct.setAddedDate(product.getAddedDate());

	    // Handle image upload
	    if (imageFile != null && !imageFile.isEmpty()) {
	        String imageName = "product_image/" + imageFile.getOriginalFilename();
	        if (fuploadService.uploadProductImage(imageFile)) {
	            existingProduct.setImageName(imageName); // Save new image name
	        }
	    } else {
	        existingProduct.setImageName(pevImg); // Keep previous image
	    }

	    // Save updated product
	    productService.updateProduct(existingProduct);

	    return "redirect:/user/product/view";
	}



//listing the logged in user products or owner products
	@GetMapping("/view/{id}")
	public String showProduct(@PathVariable int id, Model m, Principal principal) {
User user = userService.getUserById(id);

		m.addAttribute("user", user);
		
		List<Product> products = productService.getProductByUserId(user.getId());
		m.addAttribute("products", products);
		
//		List<OrderProduct> orders_list = orderService.getOrderbyUser(user);
//		m.addAttribute("orders", orders_list);
		return "product/productlist";
		
	}


	@PostMapping("/delete/{id}")
	public String deleteProduct(@PathVariable int id,RedirectAttributes redirectAttributes) {
	    Product pro = productService.getProductById(id);
	    if (pro != null) {
	        productService.deleteProduct(pro);
	        // Redirect to the product view page with a success message in query params
	        redirectAttributes.addFlashAttribute("deleteSuccess","Deleted Successfully");
	        return "redirect:/user/product/view/" + pro.getUser().getId();
	    } else {
	        // Redirect to the homepage with a failed message in query params
	    	redirectAttributes.addFlashAttribute("errordelete","Error deleting products");
	        return "redirect:/user/product/view/" + pro.getUser().getId();

	    }
	}


	
	//listing the other users products
	@GetMapping("/allproducts/{id}")
	public String getProducts(@PathVariable int id, Model m, Principal p) {
	    // Get the user and products
	    User user = userService.getUserById(id);
	    m.addAttribute("user", user);
	    System.out.println(user.getId() + " , " + user.getName());

	    List<Product> products = productService.getAllProductExceptLoggedInUser(id);
	    List<Cart> cartItems = cartService.getCartByUser(user);

	    // Loop through each product to check if it's out of stock
	    for (Product product : products) {
	        int totalCartQuantity = 0;

	        // Calculating the total quantity ordered for the current product in the cart
	        for (Cart cart : cartItems) {
	            if (cart.getProduct().getId() == product.getId()) {
	            	totalCartQuantity += cart.getQuantity();
	            }
	        }

	        // If the total quantity ordered equals the available quantity, mark it as out of stock
	        if (product.getQuantity() <= totalCartQuantity) {
	            product.setQuantity(0); // Set quantity to 0 (out of stock)
	            System.out.println("Product with ID " + product.getId() + " is now out of stock.");
	        } else {
	            // Otherwise, update the quantity to show remaining stock
	            product.setQuantity(product.getQuantity() - totalCartQuantity);
	        }
	    }

	    // Add both products and cart items to the model
	    m.addAttribute("products", products);
	    m.addAttribute("cartItems", cartItems);

	    return "user/products"; // Your view template
	}




}
