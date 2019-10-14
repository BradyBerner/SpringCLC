package com.gcu.business;

import java.util.List;
import com.gcu.data.DataAccessInterface;
import com.gcu.model.ProductModel;


/**
 * Implementation of a product business service that can handle business logic related to products
 */
public class ProductBusinessService implements ProductBusinessInterface 
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
	 * @return Whether or not the product was successfully added
	 */
	@Override
	public boolean add(ProductModel product) 
	{
		return true;
	}

	/**
	 * Method to find a single product using that product's ID
	 * @param id The ID of the product being searched for
	 * @return The product found. A -1 will be returned for ID in the event that nothing is found
	 */
	@Override
	public ProductModel findByID(int id) 
	{
		return productService.findByID(id);
	}

	/**
	 * A method that returns a list of every product in the database
	 * @return A list of all products currently in the database
	 */
	@Override
	public List<ProductModel> findAll() 
	{
		return productService.findAll();
	}

	/**
	 * A function that allows products to be edited
	 * @param The product to be edited
	 */
	@Override
	public boolean editProduct(ProductModel product)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This function removes a product from the database
	 * @param The product to be removed
	 */
	@Override
	public boolean remove(ProductModel product) 
	{
		// TODO Auto-generated method stub
		return false;
	}



}
