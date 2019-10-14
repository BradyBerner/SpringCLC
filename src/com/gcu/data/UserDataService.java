package com.gcu.data;

import java.util.List;

import com.gcu.model.CredentialsModel;
import com.gcu.model.UserModel;

/**
 * Data access service that handles database persistence logic regarding users
 *
 */
public class UserDataService implements DataAccessInterface<UserModel> {

	/**
	 * Method that handles attempted user login
	 * @param credentials The credentials a user is attempting to log in with
	 * @return The user's ID upon a success. -1 upon a failure
	 */
	public UserModel findByCredential(CredentialsModel credentials) 
	{
		if(credentials.getUsername().equals("tester") && credentials.getPassword().equals("testing"))
		{
			return new UserModel(1, "User", "Found", credentials.getUsername(), credentials.getPassword(), "Hello@World.com", "111-111-1111", 0, 1);
		}
		else
		{
			return new UserModel();
		}
	}
	
	/**
	 * Returns a list of all users found in the database
	 *@return Returns a list of all users
	 */
	@Override
	public List<UserModel> findAll() 
	{
		return null;
	}

	/**
	 * Returns a user found with a certain ID
	 * @param id The ID of the user being searched for
	 * @return A user found with a certain ID, or a blank user object
	 */
	@Override
	public UserModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * A method that finds a user in the database and returns it. Maybe useful, probably not. Who knows.
	 * @param t The user we are looking for
	 * @return The user, if found
	 */
	@Override
	public UserModel findBy(UserModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to add a user to the database
	 * @param user A user to be registered
	 * @return Returns a boolean stating success or failure of registration 
	 */
	@Override
	public boolean create(UserModel t) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * A function that allows user information to be edited
	 * @param user The user to be edited
	 */
	@Override
	public int update(UserModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A function to remove a user from the database
	 * @ param user The user to be deleted 
	 */
	@Override
	public int delete(UserModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
