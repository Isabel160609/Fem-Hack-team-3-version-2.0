package com.init.User.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.init.User.Entity.Question;
import com.init.User.service.ServicePlay;

@RestController
public class ControllerPlay {

	@Autowired
	private ServicePlay servicePlay;

	@GetMapping(value = "/login/{name}")
	public ResponseEntity<String> login(@PathVariable(name = "name") String name, @RequestBody String email) {
		try {
			String response = servicePlay.userLogin(name, email);

			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@GetMapping(value = "/check/{id}/{answer}")
	public ResponseEntity<String> correctAnswer(@PathVariable(name = "id") int id,@PathVariable(name = "answer") int answer) {
		try {
			String response = servicePlay.correctAnswer(id, answer);
			return new ResponseEntity<String>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value ="/showQuestions")
	public ResponseEntity<Optional<List<Question>>>showQuestions(){
		try {
			 Optional<List<Question>> questions=servicePlay.showQuestionsTxt();
			 if (questions.isPresent()){
				 return new ResponseEntity<>(questions, HttpStatus.FOUND);
			 }else {
				 return new ResponseEntity<>(questions, HttpStatus.NOT_FOUND);
			 }
			

		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value ="/showQuestionsJson/{id}")
	public ResponseEntity<Optional<List<Question>>>showQuestionsJson(@PathVariable int id){
		try {
			 Optional<List<Question>> questions=servicePlay.showQuestionsJson(id);
			 if (questions.isPresent()){
				 return new ResponseEntity<>(questions, HttpStatus.FOUND);
			 }else {
				 return new ResponseEntity<>(questions, HttpStatus.NOT_FOUND);
			 }
			

		} catch (Exception e) {
			return new ResponseEntity<>( HttpStatus.CONFLICT);
		}
	}
}
