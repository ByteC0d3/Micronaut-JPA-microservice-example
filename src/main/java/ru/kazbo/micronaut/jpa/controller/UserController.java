package ru.kazbo.micronaut.jpa.controller;

import java.util.List;
import jakarta.inject.Inject;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.scheduling.TaskExecutors;
import ru.kazbo.micronaut.jpa.service.UserService;
import ru.kazbo.micronaut.jpa.model.User;
import javax.validation.constraints.NotNull;

@ExecuteOn(TaskExecutors.IO)
@Controller("/users")
public class UserController {
	
	@Inject
	private UserService service;
	
	/**
	 *	Get user by his id
	 *	test this link for localhost: http://localhost:8080/users/<id>
	 *	@param id id of user in database
	 *	@return user object in JSON-string
	 */
	@Get(value = "/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
	
	/**
	 *	Get all-users from database
	 *	test this link for localhost: http://localhost:8080/users/get
	 *	@return list of users in JSON-string
	 */
	@Get(value = "/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return service.findAll();
	}
	
	/**
	 *	Remove user by ID
	 *	@param user The JSON-string of user with ID (example: {"id":"1"})
	 */
	@Post(value = "/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeUserById(User user) {
		service.remove(user.getId());
	}
	
	@Post(value = "/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(User user) {
		return service.update(user);
	}
	
	/**
	 *	Add/update user in database
	 *	example of post data: {"name":"Alex","age":"25"}
	 *	@return JSON-string of user object with ID
	 */
	@Post(value = "/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User saveUser(User user) {
		service.save(user);
		return service.findByName(user.getName());
	}
}