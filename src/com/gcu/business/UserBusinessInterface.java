package com.gcu.business;

import java.util.List;

import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;

public interface UserBusinessInterface<T>
{
		/**
		 * Method to add a user to the database
		 * @param user A user to be registered
		 * @return Returns a boolean stating success or failure of registration 
		 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied object already exists in the database, and duplicates are not allowed
		 */
		public boolean register(T user) throws ItemAlreadyExistsException;
		
		/**
		 * Method that handles attempted user login
		 * @param credentials A user model containing the credentials a user is attempting to log in with
		 * @return The user's ID upon a success. -1 upon a failure
		 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
		 */
		public T authenticate(T credentials) throws ItemNotFoundException;
		
		/**
		 * Method to find a single user using that user's ID
		 * @param id  The ID of the user being looked for
		 * @return A found user, ID will be -1 if not found
		 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
		 */
		public T findUserByID(int id) throws ItemNotFoundException;
		
		/**
		 * A method that returns a list of every user in the database
		 * @return A list of all users within the database
		 * @throws ItemNotFoundException This exception is thrown in the event that no item is found in the database
		 */
		public List<T> findAllUsers() throws ItemNotFoundException;
		
		/**
		 * A function that allows user information to be edited
		 * @param user The user to be edited
		 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
		 * @return Returns a boolean denoting whether or not the user's record was successfully updated
		 */
		public boolean editUser(T user) throws ItemNotFoundException;
		
		/**
		 * A function to remove a user from the database
		 * @param user The user to be deleted 
		 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
		 * @return Returns a boolean denoting whether or not the user was successfully removed
		 */
		public boolean removeUser(T user) throws ItemNotFoundException;
}
