package com.gcu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.UserBusinessInterface;
import com.gcu.model.UserModel;

/**
 * REST controller that currently handles CRUD methods regarding users
 */
@RestController
@RequestMapping("/userRest")
public class UserRestService 
{
	
	//Class scoped business service to handle back-end logic
	private UserBusinessInterface userService;
			
	/**
	 * This function injects a user business service at runtime for use in this particular class
	* @param userService A business service which handles any functions related to login
	*/
	@Autowired
	public void setUserBusinessService(UserBusinessInterface userService)
	{
		this.userService=userService;
	}
	
	/**
	 * REST method that returns a list of all users in the database
	 * @return
	 */
	@GetMapping("/users")
	public List<UserModel> getUsers()
	{
		return userService.findAll();
	}
	
	
	
}
