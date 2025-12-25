package com.asiya.projectbazar.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="cart")
public class Cart  implements Serializable{
	private static final long serialVersionUID=1l;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cart_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="added_date")
			private LocalDate addedDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ordered_product_id")
	private OrderedProducts orderedProducts;
	
    private boolean isOrdered; 


	public Cart() {
		super();
	}

	public Cart(int id, Product product, int quantity, LocalDate addedDate, User user, OrderedProducts orderedProducts,Boolean isOrdered) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.addedDate = addedDate;
		this.user = user;
		this.orderedProducts=orderedProducts;
		this.isOrdered=isOrdered;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderedProducts getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrderedProducts(OrderedProducts orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}


	
	
}
