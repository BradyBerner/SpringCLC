package com.gcu.business;

import java.util.List;

import com.gcu.model.ProductModel;

/**
 * Interface class for any business service that will handle products. Every product SpringBean must contain CRUD methods at a minimum
 *
 */
public interface ProductBusinessInterface 
{
	// Creation method
	public boolean add(ProductModel product);
	//Single read method
	public ProductModel findByID(int id);
	//Read all method
	public List<ProductModel> findAll();
	//Update method
	public boolean editProduct(ProductModel product);
	// Destroy method
	public boolean remove(ProductModel product);
}
