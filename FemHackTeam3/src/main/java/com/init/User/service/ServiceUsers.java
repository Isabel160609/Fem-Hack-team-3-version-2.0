package com.init.User.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.User.Entity.User;
import com.init.User.repository.UserDao;

@Service
public class ServiceUsers {

	@Autowired
	private UserDao userDao;

	// 1 list users
	public List<User> userList() {
		List<User> users = userDao.findAll();
		return users;
	}

	// create user
	public Optional<User> createUser(User user) {
		List<User> users = userDao.findAll();
		if (users.isEmpty()) {
			userDao.save(user);
			return Optional.of(user);
		} else {
			boolean repeat = false;
			int i = 0;
			while (repeat == false && i > users.size()) {
				if (user.getEmail().equals(users.get(i).getEmail())) {
					repeat = true;
				}
				i++;
			}
			if (repeat == false) {
				userDao.save(user);
				return Optional.of(user);
			} else {
				return null;
			}
		}
	}

	// find user in bbdd
	public Optional<User> getUserById(int id) {
		Optional<User> user = userDao.findById(id);
		return user;
	}

	//update  the user if is present
	public String updateUser(int id, User user) {

		Optional<User> user1 = userDao.findById(id);
		if (user1.isPresent()) {
			User userUpdate = user1.get();
			userUpdate.setName(user.getName());
			userUpdate.setEmail(user.getEmail());
			userDao.save(userUpdate);
			return "Updated user success";
		} else {
			return "User not found";
		}

	}
	//delete User by id
	public void deleteUser(int id) {

		userDao.deleteById(id);
	}
}
