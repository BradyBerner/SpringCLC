package com.gcu.data;

import java.util.List;
/**
 * Basic interface for all data access objects within the program
 * @param <T> This parameter can be any object within the application that is stored in a database
 */
public interface DataAccessInterface <T>
{
	public List<T> findAll();
	public T findByID(int id);
	public T findBy(T t);
	public boolean create(T t);
	public int update(T t);
	public int delete(T t);
	
}