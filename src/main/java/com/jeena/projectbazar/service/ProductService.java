package com.asiya.projectbazar.service;

import java.util.List;


import com.asiya.projectbazar.entity.Product;

public interface ProductService {
public Product addProduct(Product product);

public List<Product> getAllProducts();
public Product getProductById(int id);
public void updateProduct(Product product);
public void deleteProduct(Product product);
public List<Product> getProductByUserId(int id);
List<Product>getAllProductExceptLoggedInUser(int userId);

}
