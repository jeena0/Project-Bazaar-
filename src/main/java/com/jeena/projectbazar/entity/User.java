package com.asiya.projectbazar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="user")
public class User implements Serializable{


	private static final long serialVersionUID=1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="user_id")
	private int id;
	
	@NotEmpty(message="Name field is required")
	@Size(min=2, max=30, message="Name letters should be from 2 to 30 letters")
	private String name;
	
	
	
	@Column(name="email")
	@Email(message="Enter valid email")
	@NotEmpty(message="Email is required")
	private String email;
	
	
	@NotEmpty(message = "Phone is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Enter a valid 10-digit phone number")
	private String phone;

	
	
	@NotEmpty(message="Username is required")
	@Size(min=2, max=15, message="The username should be upto 15 characters long")
	private String username;
	
	@NotEmpty(message="Address is required")
	private String address;
	
	@Column(name="dob")
	private LocalDate dob;
	
	@Column(name="password")
	private String password;
	
	@Column(name="enable")
	private String enable;
	
	@Transient
	private String authority;
	
@OneToOne(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval = true)
private UserRole userRole;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Product> products = new ArrayList<>();

@OneToMany(mappedBy="user", cascade=CascadeType.ALL,orphanRemoval = true)
private List<Cart> cartList;

@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
private List<OrderProduct> orderProducts;

public User() {
	super();
}
public User(int id, String name, String email, String phone, String username, String address, LocalDate dob,
		String password, String enable, String authority, UserRole userRole, 
		String imageName,List<Cart> cartList,List<OrderProduct> orderProducts) {
	super();
	this.name = name;
	this.email = email;
	this.phone = phone;
	this.username = username;
	this.address = address;
	this.dob = dob;
	this.password = password;
	this.enable = enable;
	this.authority = authority;
	this.userRole = userRole;
	this.cartList=cartList;
	this.orderProducts=orderProducts;

}
public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public LocalDate getDob() {
	return dob;
}
public void setDob(LocalDate dob) {
	this.dob = dob;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEnable() {
	return enable;
}
public void setEnable(String enable) {
	this.enable = enable;
}
public String getAuthority() {
	return authority;
}
public void setAuthority(String authority) {
	this.authority = authority;
}
public UserRole getUserRole() {
	return userRole;
}
public void setUserRole(UserRole userRole) {
	this.userRole = userRole;
}


public List<Product> getProducts() {
	return products;
}
public void setProducts(List<Product> products) {
	this.products = products;
}
public List<Cart> getCartList() {
	return cartList;
}
public void setCartList(List<Cart> cartList) {
	this.cartList = cartList;
}
public List<OrderProduct> getOrderProducts() {
	return orderProducts;
}
public void setOrderProducts(List<OrderProduct> orderProducts) {
	this.orderProducts = orderProducts;
}






	
	
	
	

}