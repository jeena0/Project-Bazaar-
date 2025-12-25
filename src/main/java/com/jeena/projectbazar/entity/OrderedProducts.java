package com.asiya.projectbazar.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="ordered_product")
public class OrderedProducts implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 @Column(name="ordered_product_id")
	 private int ordered_id;
	 
	 @ManyToOne
	 @JoinColumn(name="order_id")
	 private OrderProduct orderProduct;
	 
	 @ManyToOne
	 @JoinColumn(name="product_id")
	 private Product product;
	 
	 private int quantity;

	public OrderedProducts() {
		super();
	}

	public OrderedProducts(int ordered_id, OrderProduct orderProduct, Product product, int quantity) {
		super();
		this.ordered_id = ordered_id;
		this.orderProduct = orderProduct;
		this.product = product;
		this.quantity = quantity;
	}

	public int getOrdered_id() {
		return ordered_id;
	}

	public void setOrdered_id(int ordered_id) {
		this.ordered_id = ordered_id;
	}

	public OrderProduct getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(OrderProduct orderProduct) {
		this.orderProduct = orderProduct;
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
	 
	 
	 
	 
	 

}
