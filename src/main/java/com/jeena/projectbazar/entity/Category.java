package com.asiya.projectbazar.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="category")
public class Category implements Serializable {
	private static final long serialVersionUID=2L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int id;
	
	
	@Column(name="category_name,unique=true")
	private String name;
	
	@Column(name="category_description")
	private String description;
	
	@Column(name="category_parent")
	private String parentCat;

	public Category() {
		super();
	}

	public Category(int id, String name, String description, String parentCat) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.parentCat = parentCat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentCat() {
		return parentCat;
	}

	public void setParentCat(String parentCat2) {
		this.parentCat = parentCat2;
	}
	
	

}
