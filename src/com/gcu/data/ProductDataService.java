package com.gcu.data;

import java.util.List;

import com.gcu.model.ProductModel;

/**
 * Data service that handles database persistence logic regarding products
 */
public class ProductDataService implements DataAccessInterface<ProductModel> {

	/**
	 * A method that returns a list of every product in the database
	 * @return A list of all products currently in the database
	 */
	@Override
	public List<ProductModel> findAll() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public ProductModel findBy(ProductModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to add a product to the database of products
	 * @param product The product to be added
	 * @return Whether or not the product was successfully added
	 */
	@Override
	public boolean create(ProductModel t) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * A function that allows products to be edited
	 * @param The product to be edited
	 */
	@Override
	public int update(ProductModel t) {
		// TODO Auto-generated method stub
		return 1;
	}

	/**
	 * This function removes a product from the database
	 * @param The product to be removed
	 */
	@Override
	public int delete(ProductModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
