package com.gcu.business;

import com.gcu.model.ProductModel;

/**
 * Implementation of a product business service that can handle business logic related to products
 */
public class ProductBusinessService implements ProductBusinessInterface {

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
		return new ProductModel(id, 1, "Found", "A product found using the findByID method", "none");
	}

	/**
	 * A method that returns an array of every product in the database
	 * @return An array of all products currently in the database
	 */
	@Override
	public ProductModel[] findAll() 
	{
		// TODO Auto-generated method stub
		return null;
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
