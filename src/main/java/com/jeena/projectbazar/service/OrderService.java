package com.asiya.projectbazar.service;

import java.util.List;

import com.asiya.projectbazar.entity.OrderProduct;
import com.asiya.projectbazar.entity.OrderedProducts;
import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;

public interface OrderService {
	public OrderProduct saveOrder(OrderProduct orderProduct);
	public List<OrderProduct> getAllOrders();
	public OrderProduct getOrderById(int id);
	public List<OrderProduct> getOrderbyUser(User user);
	public void updateOrder(OrderProduct orderProduct);
	public void deleteOrder(OrderProduct orderProduct);
	public List<OrderProduct>getOrderedProduct(User user);
	
//	public List<OrderedProducts> getMyOrders(Product product);
//	List<OrderedProducts> getMyOrders(User user);
	public List<OrderedProducts> getMyOrdersByUser(User productOwner);


//	public void deleteOrderByUserId(OrderProduct orderProduct);
	

}
