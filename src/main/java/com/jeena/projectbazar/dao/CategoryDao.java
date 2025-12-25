package com.asiya.projectbazar.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asiya.projectbazar.entity.Category;
@Repository
public interface CategoryDao  extends JpaRepository<Category,Integer>
{
	public Category findByName(String name);
	public List<Category> findByParentCat(String parentCat);
	

}
