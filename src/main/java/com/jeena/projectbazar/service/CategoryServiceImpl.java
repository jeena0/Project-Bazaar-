package com.asiya.projectbazar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiya.projectbazar.dao.CategoryDao;
import com.asiya.projectbazar.entity.Category;
@Service
@Transactional
public class CategoryServiceImpl  implements CategoryService{
	@Autowired
	private CategoryDao categoryDao;
	
	public CategoryServiceImpl(CategoryDao categoryDao)
	{
		this.categoryDao=categoryDao;
	}

	@Override
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
		Category cat= categoryDao.save(category);
		System.out.println("The primary key "+cat.getId());
		
		
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryDao.findAll();
	}

	@Override
	public Category getCategoryById(int id) {
		// TODO Auto-generated method stub
		return categoryDao.findById(id).orElse(new Category());
	}

	@Override
	public Category getCategoryByName(String name) {
		// TODO Auto-generated method stub
		return categoryDao.findByName(name);
	}

	@Override
	public List<Category> getCategory(String ParentId) {
		// TODO Auto-generated method stub
		return categoryDao.findByParentCat(ParentId);
	}

	@Override
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub
		categoryDao.save(category);
		
	}

	@Override
	public void deleteCategory(Category category) {
		// TODO Auto-generated method stub
		categoryDao.delete(category);
		
	}
	
	

}
