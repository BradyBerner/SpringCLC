package com.gcu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.ProductBusinessInterface;
import com.gcu.model.ProductModel;

/**
 * REST controller that currently handles CRUD methods regarding products
 */
@RestController
@RequestMapping("/productRest")
public class ProductRestService 
{
	//Class scoped business service to handle back-end logic
	private ProductBusinessInterface productService;
			
	/**
	* This function injects a user business service at runtime for use in this particular class
	* @param userService A business service which handles any functions related to login
	*/
	@Autowired
	public void setProductBusinessService(ProductBusinessInterface productService)
	{
		this.productService=productService;
	}

	/**
	 * REST method that returns a list of all user in the database
	 * @return
	 */
	@GetMapping("/products")
	public List<ProductModel> getProducts()
	{
		return productService.findAll();
	}

}
