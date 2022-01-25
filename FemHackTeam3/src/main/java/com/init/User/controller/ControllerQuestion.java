package com.init.User.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.init.User.Entity.Question;
import com.init.User.service.ServiceQuestions;

@RestController
public class ControllerQuestion {

	@Autowired
	private ServiceQuestions questionService;

	// return question list
	@GetMapping(value = "/questions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			List<Question> questions = questionService.questionList();
			return new ResponseEntity<List<Question>>(questions, HttpStatus.FOUND);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}

	}

	// return a new Question
	@PostMapping(value = "/questions/{id_user}")
	public ResponseEntity<String> createQuestion(@PathVariable(name = "id_user") int id,@RequestBody Question question) {
		try {
			String newQuestion = questionService.createQuestion(id,question);

			return new ResponseEntity<String>(newQuestion, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	//
	@PutMapping(value = "/questions/{id}")
	public ResponseEntity<String> updateQuestion(@PathVariable int id, @RequestBody Question question) {
		try {
			String message = questionService.updateQuestion(id, question);
			if (message.equals("Updated question success")) {

				return new ResponseEntity<>(message, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@Transactional
	@DeleteMapping(value = "questions/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable(name = "id") int id) {
		try {
			Optional<Question> question = questionService.getQuestionById(id);

			if (question.isPresent()) {
				questionService.deleteQuestion(id);
				return new ResponseEntity<>("Question successfully deleted", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
