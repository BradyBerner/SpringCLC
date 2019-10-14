package com.gcu.business;

import java.util.List;

import com.gcu.data.DataAccessInterface;
import com.gcu.data.UserDataService;
import com.gcu.model.CredentialsModel;
import com.gcu.model.UserModel;

/**
 * Implementation of a user business service that can handle business logic related to users
 */
public class UserBusinessService implements UserBusinessInterface 
{
	
	//Class scoped data service injected at runtime
	DataAccessInterface<UserModel> userService;
	
	/**
	 * This method injects a data Service at runtime to be used within the business service
	 * @param userService The data service to be injected
	 */
	public void setUserDataService(DataAccessInterface<UserModel> userService)
	{
		this.userService=userService;
	}
	
	/**
	 * Method that handles attempted user login
	 * @param credentials The credentials a user is attempting to log in with
	 * @return The user's ID upon a success. -1 upon a failure
	 */
	@Override
	public int authenticate(CredentialsModel credentials) 
	{
		//Unable to use findByCredential method as it implements the generic DataAccessInterface
		UserModel user = ((UserDataService)userService).findByCredential(credentials);
		return user.getID();
	}
	
	/**
	 * Method to add a user to the database
	 * @param user A user to be registered
	 * @return Returns a boolean stating success or failure of registration 
	 */
	@Override
	public boolean register(UserModel user) 
	{
		return true;
	}
	
	/**
	 * Method to find a single user using that user's ID
	 * @param id  The ID of the user being looked for
	 * @return A found user, ID will be -1 if not found
	 */
	@Override
	public UserModel findByID(int id) 
	{
		return userService.findByID(id);
	}

	/**
	 * A method that returns a list of every user in the database
	 * @return A list of all users within the database
	 */
	@Override
	public List<UserModel> findAll() 
	{
		return userService.findAll();
	}

	/**
	 * A function that allows user information to be edited
	 * @param user The user to be edited
	 */
	@Override
	public boolean editUser(UserModel user) 
	{
		return true;
	}

	/**
	 * A function to remove a user from the database
	 * @ param user The user to be deleted 
	 */
	@Override
	public boolean remove(UserModel user) 
	{
		
		return true;
	}

}
