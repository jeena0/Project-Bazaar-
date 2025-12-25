package com.asiya.projectbazar.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.asiya.projectbazar.entity.OrderedProducts;
import com.asiya.projectbazar.entity.User;


@Repository
public interface OrderedProductDao extends JpaRepository<OrderedProducts, Integer> {

//    // Fetch ordered products by product reference
	@Query("SELECT op FROM OrderedProducts op " +
		       "JOIN op.product p " +
		       "JOIN op.orderProduct o " +
		       "WHERE p.user = :productOwner AND o.user <> :productOwner")
		List<OrderedProducts> findOrdersByProductOwner(@Param("productOwner") User productOwner);


}
