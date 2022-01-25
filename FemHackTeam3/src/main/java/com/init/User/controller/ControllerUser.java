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

import com.init.User.Entity.User;
import com.init.User.service.ServiceUsers;

@RestController
public class ControllerUser {

	@Autowired
	private ServiceUsers userService;
	
	//return user list -> userList
	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = userService.userList();
			return new ResponseEntity<List<User>>(users, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}	
	
	
	//return a user User -> createUser
	@PostMapping(value = "/users")
	public ResponseEntity <Optional<User>> createUser(@RequestBody User user) {
		try {
			Optional<User> userCreated = userService.createUser(user);
			if(!userCreated.isEmpty()) {
				return new ResponseEntity<Optional<User>>(userCreated, HttpStatus.CREATED);
			}else {
				
			}return new ResponseEntity<>( HttpStatus.NOT_FOUND);
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	//
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody User user) {
		try {			
				String message = userService.updateUser(id, user);
				if(message.equals("Updated user success")) {
					
				return new ResponseEntity<String>(message, HttpStatus.FOUND);
			}else {
				return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}
	
	@Transactional
    @DeleteMapping(value = "users/{userID}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "userID") int id) {
        try {
            Optional<User> user = userService.getUserById(id);

            if (user.isPresent()) {
                userService.deleteUser(id);
                return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}