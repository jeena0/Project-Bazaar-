package com.asiya.projectbazar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
@Entity
@Table(name="mota")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;
	
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private double price;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="description")
	private String description;
	
	private LocalDate addedDate;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Transient
	private MultipartFile imageFile;
	
	
	private String imageName;
	
	@OneToMany(mappedBy="product",cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Cart> cartList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderedProducts> orderedProducts;

	
	public Product() {
		super();
	}

	public Product(int id,String name, double price, int quantity, String description, LocalDate addedDate,
			Category category, MultipartFile imageFile, String imageName, List<Cart> cartList, List<OrderedProducts> orderedProducts) {
		super();
		this.id=id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.addedDate = addedDate;
		this.category = category;
		this.imageFile = imageFile;
		this.imageName = imageName;
		this.cartList = cartList;
		this.orderedProducts=orderedProducts;
		
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderedProducts> getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrderedProducts(List<OrderedProducts> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

}
