package com.gcu.business;

import java.util.List;
import com.gcu.data.DataAccessInterface;
import com.gcu.model.ProductModel;
import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;


/**
 * Implementation of a product business service that can handle business logic related to products
 */
public class ProductBusinessService implements ProductBusinessInterface<ProductModel> 
{
	//Class scoped data service injected at runtime
	DataAccessInterface<ProductModel> productService;
	
	/**
	 * This method injects a data Service at runtime to be used within the business service
	 * @param productService The data service to be injected
	 */
	public void setProductDataService(DataAccessInterface<ProductModel> productService)
	{
		this.productService=productService;
	}
	
	/**
	 * Method to add a product to the database of products
	 * @param product The product to be added
	 * @return boolean Whether or not the product was successfully added
	 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied object already exists in the database, and duplicates are not allowed
	 */
	@Override
	public boolean add(ProductModel product) throws ItemAlreadyExistsException
	{
		return true;
	}

	/**
	 * Method to find a single product using that product's ID
	 * @param id The ID of the product being searched for
	 * @return ProductModel The product found. A -1 will be returned for ID in the event that nothing is found
	 * @throws ItemNotFoundException This exception is thrown in the event that no item is found in the database
	 */
	@Override
	public ProductModel findByID(int id) throws ItemNotFoundException
	{
		return productService.findByID(id);
	}

	/**
	 * A method that returns a list of every product in the database
	 * @return List<ProductModel> A list of all products currently in the database
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Override
	public List<ProductModel> findAll() throws ItemNotFoundException
	{
		return productService.findAll();
	}

	/**
	 * A function that allows products to be edited
	 * @param boolean product product to be edited
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Override
	public boolean editProduct(ProductModel product) throws ItemNotFoundException
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This function removes a product from the database
	 * @param product product to be removed
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Override
	public boolean remove(ProductModel product) throws ItemNotFoundException
	{
		// TODO Auto-generated method stub
		return false;
	}



}
