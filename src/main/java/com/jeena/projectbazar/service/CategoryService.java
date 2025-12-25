package com.asiya.projectbazar.service;

import java.util.List;


import com.asiya.projectbazar.entity.Category;

public interface CategoryService {
	public void addCategory(Category category);
	public List<Category> getAllCategories();
	
	public Category getCategoryById(int id);
	public Category getCategoryByName(String name);
	public List<Category> getCategory(String ParentId);
	public void updateCategory(Category category);
	public void deleteCategory(Category category);

}
