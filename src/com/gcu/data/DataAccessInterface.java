package com.gcu.data;

import java.util.List;
/**
 * Basic interface for all data access objects within the program
 * @param <T> This parameter can be any object within the application that is stored in a database
 */
public interface DataAccessInterface <T>
{
	/**
	 * Returns "all" of a certain object from the database. In the case of large tables, the service that implements this method will determine at what point to stop returning data
	 * @return List<T> A list of all of a certain object in the database
	 */
	public List<T> findAll();
	
	/**
	 * Returns an object with a specific ID
	 * @param id The id of the object for which the method is searching
	 * @return T The object found with this ID, or a default object if none is found
	 */
	public T findByID(int id);
	
	/**
	 * Returns an object back to the business service if that object is found in the database
	 * @param t An object to be searched for in the database
	 * @return T The same object if found, a default object if not found
	 * TODO: Validate that queries are case-sensitive
	 */
	public T findBy(T t);
	
	/**
	 * A method to create an object in the database
	 * @param t The object to be persisted to the database
	 * @return int The number of affected database rows
	 * TODO: Validate that an email is unique in the database
	 */
	public int create(T t);
	
	/**
	 * A method to update a certain object in the database
	 * @param t The updated object
	 * @return int The number of affected database rows
	 */
	public int update(T t);
	
	/**
	 * Removes an object from the database
	 * @param t The object to be removed
	 * @return int The number of affected database rows
	 */
	public int delete(T t);
	
}