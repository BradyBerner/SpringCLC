package com.gcu.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcu.data.DataAccessInterface;
import com.gcu.model.UserModel;
import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;

/**
 * Implementation of a user business service that can handle business logic related to users
 */
public class UserBusinessService implements UserBusinessInterface<UserModel> 
{
	
	//Class scoped data service injected at runtime
	DataAccessInterface<UserModel> userService;
	
	/**
	 * This method injects a data Service at runtime to be used within the business service
	 * @param userService The data service to be injected
	 */
	@Autowired
	public void setUserDataService(DataAccessInterface<UserModel> userService)
	{
		this.userService=userService;
	}
	
	/**
	 * Method that handles attempted user login
	 * @param credentials A user model containing the credentials a user is attempting to log in with
	 * @return UserModel the user found within the database
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Override
	public UserModel authenticate(UserModel credentials) throws ItemNotFoundException
	{
		//Attempting to find user in database
		UserModel foundUser = userService.findBy(credentials);
		//Returning user if one is found
		if(foundUser.getID()>0)
			return foundUser;
		//Throwing exception if default user object is returned
		else
			throw new ItemNotFoundException();
	}

	//TODO: Fix phone number
	/**
	 * Method to add a user to the database
	 * @param user A user to be registered
	 * @return Returns a boolean stating success or failure of registration 
	 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied object already exists in the database, and duplicates are not allowed
	 */
	@Override
	public boolean register(UserModel user) throws ItemAlreadyExistsException
	{
		//If a user with the selected username already exists, returns an exception to the controller
		//Checking for a valid user with the provided username
		int foundID= userService.findByString(user.getCredentials().getUsername()).getID();
		//Returning an exception if a valid user is found
		if(foundID>0)
			throw new ItemAlreadyExistsException();
		
		//Attempts to persist a user to the database
		int response = userService.create(user);
		
		//Checking for valid output
		if(response>0)
		{
			return true;
		}
		//Checking for failed addition and returning response
		else
		{
			return false;
		}
	}
	
	/**
	 * Method to find a single user using that user's ID
	 * @param id  The ID of the user being looked for
	 * @return A found user, ID will be -1 if not found
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Override
	public UserModel findByID(int id) throws ItemNotFoundException
	{
		return userService.findByID(id);
	}

	/**
	 * A method that returns a list of every user in the database
	 * @return A list of all users within the database
	 * @throws ItemNotFoundException This exception is thrown in the event that no item is found in the database
	 */
	@Override
	public List<UserModel> findAll() throws ItemNotFoundException
	{
		return userService.findAll();
	}

	/**
	 * A function that allows user information to be edited
	 * @param user The user to be edited
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Override
	public boolean editUser(UserModel user) throws ItemNotFoundException
	{
		return true;
	}

	/**
	 * A function to remove a user from the database
	 * @param user The user to be deleted 
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Override
	public boolean remove(UserModel user) throws ItemNotFoundException
	{
		return true;
	}

}
