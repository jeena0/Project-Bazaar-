package com.asiya.projectbazar.controller;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.asiya.projectbazar.dto.PaymentDto;
import com.asiya.projectbazar.entity.Cart;
import com.asiya.projectbazar.entity.User;
import com.asiya.projectbazar.service.PaymentService;
import com.asiya.projectbazar.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PaymentController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("user/payment/esewa/{id}")
	public String payWithEsewa(@PathVariable int id, Principal p, Model m) {
		User user=userService.getUserById(id);
		m.addAttribute("user", user);
		PaymentDto payment=new PaymentDto();
		List<Cart> cartList=user.getCartList();
		double amount=0;
		for(Cart cart:cartList) {
			amount+=(cart.getProduct().getPrice()*cart.getQuantity());
		}
		payment.setAmount(amount);
		payment.setTax_amount(100);
		payment.setPdc(100);
		payment.setPsc(100);
		payment.setTotal_amount(Math.ceil(100+100+100+amount));
		payment.setProduct_code("EPAYTEST");
		payment.setTransactionalUUID(UUID.randomUUID()+"-OC81");
		payment.setSigned_field_name("total_amount,transaction_uuid,product_code");
		String message="total_amount="+payment.getTotal_amount()+",transaction_uuid="+payment.getTransactionalUUID()+
				",product_code="+payment.getProduct_code();
		payment.setSignature(paymentService.EsewaPaymentSignature("8gBm/:&EnhH.1/q",message));
		payment.setSuccess_url("http://localhost:7070/user/payment/success/esewa");
		payment.setFailure_url( "http://localhost:7070/user/home");
		
		m.addAttribute("payment",payment);
		return"user/payments";
		
	}
	
	
	@GetMapping("user/khalti/{id}")
		public String paykhalti(@PathVariable int id, Principal p,Model m) {
		User user=userService.getUserById(id);
		m.addAttribute("user",user);
		PaymentDto payment=new PaymentDto();
		List<Cart> cartList=user.getCartList();
		double amount=0;
		for(Cart cart:cartList) {
			amount+=(cart.getProduct().getPrice()*cart.getQuantity());
		}
		payment.setAmount(amount);
		payment.setTax_amount(100);
		payment.setPdc(100);
		payment.setPsc(100);
		payment.setTotal_amount(Math.ceil(100+100+100+amount));
		m.addAttribute("payment",payment);
		
			return "user/khaltipay";
		}
	
	
	@PostMapping("user/payment/khalti")
	public RedirectView khaltipayment() {
	    String url = "https://dev.khalti.com/api/v2/epayment/initiate/";
//The endpoint url to initiate the khalti environment
	    
	    // Request bodies
	    Map<String, Object> requestBody = new HashMap<>();
	    requestBody.put("return_url", "http://localhost:7070/user/khalti/{id}");
	    requestBody.put("website_url", "http://localhost:7070/user/home");
	    requestBody.put("amount", 1000); // Amount in paisa
	    requestBody.put("purchase_order_id", "Order01");
	    requestBody.put("purchase_order_name", "Test Purchase");

	    Map<String, String> customerInfo = new HashMap<>();
	    customerInfo.put("name", "Test Bahadur");
	    customerInfo.put("email", "test@khalti.com");
	    customerInfo.put("phone", "9800000001");
	    requestBody.put("customer_info", customerInfo);

	    // Headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "key live_secret_key_68791341fdd94846a146f0457ff7b455");

	    // HTTP entity containing headers and body
	    //
	    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
	    RestTemplate restTemplate = new RestTemplate();

	    // Response and redirection
	    try {
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
	      	        //created the post request for the api and sending through restTemplate
	        
	        //to process the response the JSON response and extracting the payment url
	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);

	        String paymentUrl = (String) responseMap.get("payment_url");
	        RedirectView redirectView = new RedirectView();
	        redirectView.setUrl(paymentUrl);
	        return redirectView;
	    } catch (Exception e) {
	        throw new RuntimeException("Error initiating Khalti payment: " + e.getMessage(), e);
	    }
	}

	
 

    
}
