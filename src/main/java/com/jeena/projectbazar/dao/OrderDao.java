package com.asiya.projectbazar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asiya.projectbazar.entity.OrderProduct;
import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;

@Repository
public interface OrderDao extends JpaRepository<OrderProduct,Integer> {
	public List<OrderProduct> findByUser(User user);

	public List<OrderProduct> findById(int id);

	
	}
	


