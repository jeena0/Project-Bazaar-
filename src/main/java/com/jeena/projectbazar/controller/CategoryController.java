package com.asiya.projectbazar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asiya.projectbazar.entity.Category;
import com.asiya.projectbazar.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/add")
	public String addCategory(Principal p, Model model) {
		String username=p.getName();
		model.addAttribute("user", username);
		model.addAttribute("cat_list",categoryService.getAllCategories());
		return "product/category";
	}
	
@PostMapping("/add")
public String saveCategory(@RequestParam("cname")String name,
		@RequestParam("cat_description")String description,
		@RequestParam("cat_parent")String parentCat) {
	Category category = new Category();
	category.setName(name);
	category.setDescription(description);
	category.setParentCat(parentCat);
	categoryService.addCategory(category);
	return "redirect:/admin/category/add";
	
}
@GetMapping("/edit/{cat_id}")
public String editCategory(@PathVariable("cat_id")int id,Model model) {
	model.addAttribute("edit",true);
	model.addAttribute("cat_list",categoryService.getAllCategories());
	model.addAttribute("edit_cat",categoryService.getCategoryById(id));
	return"product/category";
}
@PostMapping("/edit")
public String edit(@ModelAttribute Category category) {
	categoryService.updateCategory(category);
	return"redirect:/admin/category/show#category_data";
}

@GetMapping("/show")
public String showCategory(Model model) {
	model.addAttribute("cat_list",categoryService.getAllCategories());
	return "product/category";
}



@PostMapping("/delete/{id}")
public String deleteCategory(@PathVariable int id)
{
	Category cat=categoryService.getCategoryById(id);
	if(cat!=null) {
	categoryService.deleteCategory(cat);
	return "redirect:/admin/category/show##category_data?delete=success";
} else {
	return"redirect?/admin/category/show##category_data?delete=failed";
}
}
}
