package com.gcu.business;

import java.util.List;

import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;

/**
 * Interface class for any business service that will handle products. Every product SpringBean must contain CRUD methods at a minimum
 *
 */
public interface ProductBusinessInterface<T>
{
	/**
	 * Method to add a product to the database of products
	 * @param product The product to be added
	 * @return boolean Whether or not the product was successfully added
	 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied object already exists in the database, and duplicates are not allowed
	 */
	public boolean addProduct(T product) throws ItemAlreadyExistsException;
	
	/**
	 * Method to find a single product using that product's ID
	 * @param id The ID of the product being searched for
	 * @return ProductModel The product found. A -1 will be returned for ID in the event that nothing is found
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public T findProductByID(int id) throws ItemNotFoundException;
	
	/**
	 * A method that returns a list of every product in the database
	 * @return List<ProductModel> A list of all products currently in the database
	 * @throws ItemNotFoundException This exception is thrown in the event that no item is found in the database
	 */
	public List<T> findAllProducts() throws ItemNotFoundException;
	
	/**
	 * A function that allows products to be edited
	 * @param product product product to be edited
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public boolean editProduct(T product) throws ItemNotFoundException;
	
	/**
	 * This function removes a product from the database
	 * @param product product to be removed
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public boolean removeProduct(T product) throws ItemNotFoundException;
}
