package com.asiya.projectbazar.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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

@Entity
@Table(name="orders")
public class OrderProduct  implements Serializable{
	private static final long serialVersionUID=1l;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="order_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="orderProduct",cascade=CascadeType.ALL)
	private List<OrderedProducts> orderedProducts;
	
	private double total_amount;
	private double discount;
	
	public enum Status{
		Approved,Pending,Declined
	}
	
	

	 
	@Column(name="date")
	private LocalDate order_date;
	
	@Column(name="delivery_date")
	private LocalDate delivery_date;
	
	private String payment_method;
	
	private String status;

	public OrderProduct() {
		super();
	}

	public OrderProduct(int id, User user, double total_amount, double discount, LocalDate order_date,
			LocalDate delivery_date, String payment_method, String status,List<OrderedProducts> orderedProducts) {
		super();
		this.id = id;
		this.user = user;
		this.total_amount = total_amount;
		this.discount = discount;
		this.order_date = order_date;
		this.delivery_date = delivery_date;
		this.payment_method = payment_method;
		this.status = status;
		this.orderedProducts=orderedProducts;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public LocalDate getOrder_date() {
		return order_date;
	}

	public void setOrder_date(LocalDate order_date) {
		this.order_date = order_date;
	}

	public LocalDate getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(LocalDate delivery_date) {
		this.delivery_date = delivery_date;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderedProducts> getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrderedProducts(List<OrderedProducts> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	


	
	
	
	
	

}
