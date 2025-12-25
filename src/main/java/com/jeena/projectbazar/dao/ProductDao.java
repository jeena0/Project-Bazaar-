package com.asiya.projectbazar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asiya.projectbazar.entity.Product;
@Repository
public interface ProductDao extends JpaRepository<Product,Integer>
{
	
    List<Product> findByUserId(int userId);
    
    @Query("SELECT p FROM Product p WHERE p.user.id != :loggedInUserId")
    List<Product> findAllExceptLoggedInUser(@Param("loggedInUserId") int userID);
}
