package com.init.User.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.User.Entity.Question;
import com.init.User.Entity.User;
import com.init.User.repository.QuestionDao;
import com.init.User.repository.UserDao;

@Service
public class ServiceQuestions {

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private UserDao userDao;

	// 1 list users
	public List<Question> questionList() {
		List<Question> questions = questionDao.findAll();
		return questions;
	}

	// create user
	public String createQuestion(int id,Question question) {	
		Optional<User> user=userDao.findById(id);
		if(!user.isEmpty()) {
			User userpdate=user.get();
			userpdate.addQuestion(question);
			userDao.save(userpdate);
			return "question save";
		}
		else {
			return "Don't exist user";
		}
	}

	// find user in bbdd
	public Optional<Question> getQuestionById(int id) {
		Optional<Question> question = questionDao.findById(id);
		return question;
	}

	public String updateQuestion(int id, Question question) {

		Optional<Question> question1 = questionDao.findById(id);
		if (question1.isPresent()) {
			Question questionUpdate = question1.get();
			
			questionUpdate.setQuestions(question.getQuestions());
			questionUpdate.setAnswers(question.getAnswers());
			questionUpdate.setSolutions(question.getSolutions());
			
			questionDao.save(questionUpdate);
			return "Updated question success";
		} else {
			return "Question not found";
		}

	}

	public void deleteQuestion(int id) {

		questionDao.deleteById(id);
	}
}