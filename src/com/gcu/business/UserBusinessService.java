package com.gcu.business;

import com.gcu.model.CredentialsModel;
import com.gcu.model.UserModel;

/**
 * Implementation of a user business service that can handle business logic related to users
 */
public class UserBusinessService implements UserBusinessInterface 
{

	/**
	 * Method that handles attempted user login
	 * @param credentials The credentials a user is attempting to log in with
	 * @return The user's ID upon a success. -1 upon a failure
	 */
	@Override
	public int authenticate(CredentialsModel credentials) 
	{
		if(credentials.getUsername().equals("tester") && credentials.getPassword().equals("testing"))
		{
			return 1;
		}
		else
		{
			return 0;
		}
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
		return new UserModel(id, "User", "Found", "UsingFindBy", "password", "Hello@World.com", "111-111-1111", 0, 1);
	}

	/**
	 * A method that returns an array of every user in the database
	 * @return An array of all users within the database
	 */
	@Override
	public UserModel[] findAll() 
	{
		// TODO Auto-generated method stub
		return null;
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
