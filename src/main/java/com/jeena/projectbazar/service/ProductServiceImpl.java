package com.asiya.projectbazar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.asiya.projectbazar.dao.ProductDao;
import com.asiya.projectbazar.entity.Product;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl  implements ProductService{
@Autowired
private ProductDao productDao;
	
	public ProductServiceImpl(ProductDao productDao){
		this.productDao=productDao;
	}
	@Override
	@PreAuthorize("hasRole('USER') ")
	public Product addProduct(Product product) {
        System.out.println("Saving product: " + product);

		return productDao.save(product);	
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productDao.findAll();
	}

	@Override
	public Product getProductById(int id) {
		// TODO Auto-generated method stub

		return productDao.findById(id).get();
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
	productDao.saveAndFlush(product);
		
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		productDao.delete(product);
		
	}
	@Override
	public List<Product> getProductByUserId(int id) {
		
		return productDao.findByUserId(id);
	}
	@Override
	public List<Product> getAllProductExceptLoggedInUser(int userID) {
		// TODO Auto-generated method stub
		return productDao.findAllExceptLoggedInUser(userID);
	}

}
