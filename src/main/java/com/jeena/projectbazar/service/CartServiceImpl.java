package com.asiya.projectbazar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiya.projectbazar.dao.CartDao;
import com.asiya.projectbazar.entity.Cart;
import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService{

	@Autowired 
	private CartDao cartDao;
	
	@Override
	public void saveCart(Cart cart) {
		// TODO Auto-generated method stub
		cartDao.save(cart);
		
	}

	@Override
	public List<Cart> getCartByUser(User user) {
		// TODO Auto-generated method stub
		return cartDao.findByUser(user);
	}

	@Override
	public Optional<Cart> getCartById(int id) {
		// TODO Auto-generated method stub
		return cartDao.findById(id);
	}

	@Override
	public void deleteCart(Cart cart) {
		// TODO Auto-generated method stub
		
		cartDao.delete(cart);
		
	}

	@Override
	public void updateCart(Cart cart) {
		// TODO Auto-generated method stub
		cartDao.saveAndFlush(cart);
		
	}

	

	@Override
	public Optional<Cart> getCartByUserAndProduct(User user, Product product) {
		// TODO Auto-generated method stub
        return cartDao.findByUserAndProduct(user, product);
	}

	@Override
	public void deleteUserCart(User user) {
		// TODO Auto-generated method stub
		List<Cart> carts = cartDao.findByUser(user);
	    if (!carts.isEmpty()) {
	        cartDao.deleteAll(carts);
	    }
	}

	@Override
	public List<Cart> selectedCartItems(User user) {
		// TODO Auto-generated method stub
		return cartDao.findByUser(user);
	}
	


}
