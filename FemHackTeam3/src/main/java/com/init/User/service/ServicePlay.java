package com.init.User.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.init.User.Entity.Question;
import com.init.User.Entity.User;
import com.init.User.repository.QuestionDao;
import com.init.User.repository.UserDao;

@Service
public class ServicePlay {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserDao userDao;

	// Login & Sign up
	public String userLogin(String name, String email) {
		String message;
		Boolean found = false;
		List<User> users = userDao.findAll();
		if (!users.isEmpty()) {

			int i = 0;
			while (found == false && i < users.size()) {
				if (name.equals(users.get(i).getName()) && email.equals(users.get(i).getEmail())) {

					found = true;
				}
				i++;
			}
		}
		if (found) {
			message = "Login success";
		} else {
			message = "Login unsuccess";
		}
		return message;
	}

	// A route that allows to check if the choosen answer by the player is correct

	public String correctAnswer(int idQuestion, int answer) {
		String response;
		Optional<Question> OptionalQuestion = questionDao.findById(idQuestion);
		if (OptionalQuestion.isPresent()) {
			Question question = OptionalQuestion.get();
			if (answer == question.getSolutions()) {
				response = "Correct Answer";
			} else {
				response = "Wrong Answer";
			}
		} else {
			response = "This Question don't exist";
		}
		return response;
	}

	// A route that returns a set of 10 questions (randomly selected from a txt,
	// these can be fake questions) with 4 possible answers.

	public Optional<List<Question>> showQuestionsTxt() {
		String currentPath = System.getProperty("user.dir");
		String route = currentPath + "\\questions.txt";
		Optional<List<Question>> questions = LeerTxt(new File(route));
		if (questions.isPresent()) {

			List<Question> posibleQuestions = questions.get();
			List<Question> realyQuestions = new ArrayList<Question>();
			List<Integer> random = randomWhithoutRepeat(10, 10);
			for (Integer integer : random) {
				realyQuestions.add(posibleQuestions.get(integer));
			}

			return Optional.ofNullable(realyQuestions);

		} else {
			return questions;
		}

	}

	public Optional<List<Question>> showQuestionsJson(int id) {
		String currentPath = System.getProperty("user.dir");
		String route = currentPath + "\\questions2.json";
		Optional<List<Question>> questions = LeerJson(new File(route), id);
		if (questions.isPresent()) {

			List<Question> posibleQuestions = questions.get();
			List<Question> realyQuestions = new ArrayList<Question>();
			List<Integer> random = randomWhithoutRepeat(10, 10);
			for (Integer integer : random) {
				realyQuestions.add(posibleQuestions.get(integer));
			}

			return Optional.ofNullable(realyQuestions);

		} else {
			return Optional.empty();
		}

	}

	// return nQuestion of numbers random from maxQuestion of numbers
	public List<Integer> randomWhithoutRepeat(int nQuestion, int maxQuestion) {
		int pos;

		List<Integer> pQuestions = new ArrayList<Integer>();
		for (int i = 0; i < nQuestion; i++) {
			pos = (int) Math.floor(Math.random() * maxQuestion);
			while (pQuestions.contains(pos)) {
				pos = (int) Math.floor(Math.random() * maxQuestion);
			}
			pQuestions.add(pos);
		}
		return pQuestions;
	}

	static Optional<List<Question>> LeerTxt(File file) {
		Question question = new Question();
		List<Question> questions = new ArrayList<Question>();
		// inicializamos el lector
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(file));

			String linea;

			while ((linea = bufferedReader.readLine()) != null) {
				question = new Gson().fromJson(linea, Question.class);
				questions.add(question);
			}
			return Optional.ofNullable(questions);
		} catch (FileNotFoundException ex) {
			System.err.println("fichero no existe " + ex);
			return null;
		} catch (IOException ex) {
			System.err.println("no es posible extraer una pregunta " + ex);
			return null;
		} finally {

			try {
				bufferedReader.close();
			} catch (IOException ex) {
				System.err.println("no es posible extraer una pregunta " + ex);
				return null;
			}
		}

	}

	public Optional<List<Question>> LeerJson(File file, int id) {
		Question question = new Question();
		List<Question> questions = new ArrayList<Question>();
		Optional<User> user = userDao.findById(id);
		if (user.isPresent()) {
			// inicializamos el lector
			BufferedReader bufferedReader = null;

			try {

				bufferedReader = new BufferedReader(new FileReader(file));

				String linea;

				int i = 1;

				while ((linea = bufferedReader.readLine()) != null) {

					if (i == 1) {
						i++;
					} else if (i > 1 && i < 11) {
						linea = linea.substring(0, linea.length() - 1);
						question = new Gson().fromJson(linea, Question.class);

						User userFind = user.get();
						question.setUser(userFind);
						questionDao.save(question);

						questions.add(question);

						i++;
					} else if (i == 11) {

						question = new Gson().fromJson(linea, Question.class);

						User userFind = user.get();
						question.setUser(userFind);
						questionDao.save(question);

						questions.add(question);
						i++;
					} else {
						i++;
					}

				}
				return Optional.ofNullable(questions);
			} catch (FileNotFoundException ex) {
				System.err.println("fichero no existe " + ex);
				return null;
			} catch (IOException ex) {
				System.err.println("no es posible extraer una pregunta " + ex);
				return null;
			} finally {

				try {
					bufferedReader.close();
				} catch (IOException ex) {
					System.err.println("no es posible extraer una pregunta " + ex);
					return null;
				}
			}
		}else {
			return null;
		}
	}
}
