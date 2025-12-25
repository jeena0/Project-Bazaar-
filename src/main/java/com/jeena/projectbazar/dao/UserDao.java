package com.asiya.projectbazar.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asiya.projectbazar.entity.User;

public interface UserDao  extends JpaRepository<User,Integer>{
	public User findByUsername(String username);
	public User findByEmail(String email);
//	List <User> findByStatus(User.Status status);
	public User findById(int id);

}
