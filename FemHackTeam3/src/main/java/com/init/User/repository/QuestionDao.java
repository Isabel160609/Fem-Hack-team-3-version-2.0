package com.init.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.User.Entity.Question;

public interface QuestionDao extends JpaRepository<Question,Integer>{

}
