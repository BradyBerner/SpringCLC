package com.gcu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.UserBusinessInterface;
import com.gcu.model.UserModel;
import com.gcu.utility.ItemNotFoundException;

/**
 * REST controller that currently handles CRUD methods regarding users
 */
@RestController
@RequestMapping("/userRest")
public class UserRestService 
{
	
	//Class scoped business service to handle back-end logic
	private UserBusinessInterface<UserModel> userService;
			
	/**
	 * This function injects a user business service at runtime for use in this particular class
	* @param userService A business service which handles any functions related to login
	*/
	@Autowired
	public void setUserBusinessService(UserBusinessInterface<UserModel> userService)
	{
		this.userService=userService;
	}
	
	/**
	 * REST method that returns a list of all users in the database
	 * @return Returns a list of all users
	 */
	@GetMapping("/users")
	public List<UserModel> getUsers()
	{
		try 
		{
			return userService.findAllUsers();
		} 
		catch (ItemNotFoundException e) 
		{
			return new ArrayList<UserModel>();
		}
	}
	
	
	
}
