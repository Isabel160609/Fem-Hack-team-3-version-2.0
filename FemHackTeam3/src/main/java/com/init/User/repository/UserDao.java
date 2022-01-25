package com.init.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.User.Entity.User;

public interface UserDao extends JpaRepository<User,Integer>{

}
