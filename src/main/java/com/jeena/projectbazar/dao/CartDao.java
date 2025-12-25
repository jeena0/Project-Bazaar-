package com.asiya.projectbazar.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asiya.projectbazar.entity.Cart;
import com.asiya.projectbazar.entity.Product;
import com.asiya.projectbazar.entity.User;

@Repository
public interface CartDao extends JpaRepository<Cart,Integer> {
	public List<Cart> findByUser(User user);
	
    Optional<Cart> findByUserAndProduct(User user, Product product);
//    
//    @Modifying
//    @Query("DELETE FROM Cart c WHERE c.orderedProduct.id = :orderId AND c.product.id = :productId")
//    void deleteOrderCart(@Param("orderId") Long orderId, @Param("productId") Long productId);

	
	@Modifying
	@Query("delete from Cart c where c.user=:user")
	public void deleteUserCart(@Param("user")User user);
	
	
//	@Modifying
//	public List<Cart> findSelectedCartForPayment(Cart cart);
//	

}
