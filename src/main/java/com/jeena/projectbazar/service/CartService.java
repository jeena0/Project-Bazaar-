package com.asiya.projectbazar.service;

import java.util.List;
import java.util.Optional;

import com.asiya.projectbazar.entity.Cart;
import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;

public interface CartService {
	public void saveCart(Cart cart);
	public List<Cart> getCartByUser(User user);
	Optional<Cart> getCartById(int id);
	public void deleteCart(Cart cart);
	public void updateCart(Cart cart);
	public void deleteUserCart(User user);
	//public void deleteCart(
     Optional<Cart> getCartByUserAndProduct(User user, Product product);
     
     public List<Cart> selectedCartItems(User user);

}
