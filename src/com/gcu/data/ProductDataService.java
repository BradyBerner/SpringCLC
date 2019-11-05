package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import com.gcu.model.ProductModel;
import com.gcu.utility.DatabaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

/**
 * Data service that handles database persistence logic regarding products
 */
public class ProductDataService implements DataAccessInterface<ProductModel> 
{

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/**
	 * A method that returns a list of every product in the database
	 * @return A list of all products currently in the database
	 */
	@Override
	public List<ProductModel> findAll() 
	{
		//SQL Statement
		String sql = "SELECT * FROM springCLC.PRODUCTS";
		
		//Creates an arraylist so that a valid list is returned regardless of the result of the query
		ArrayList<ProductModel> products = new ArrayList<>();

		//Attempts to query the database
		try
		{
			//Binding data and getting result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);

			//Populating the found data if a valid result is returned
			while(srs.next()){
				products.add(new ProductModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("DESCRIPTION"), srs.getString("GENRE")));
			}
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}

		//Returning the populated list to the business
		return products;
	}

	/**
	 * Method to find a single product using that product's ID
	 * @param id The ID of the product being searched for
	 * @return The product found. A -1 will be returned for ID in the event that nothing is found
	 */
	@Override
	public ProductModel findByID(int id) 
	{
		//SQL Statement
		String sql = "SELECT * FROM springCLC.PRODUCTS WHERE ID = ?";
		
		//Creating a default product to be returned no matter what
		ProductModel product = new ProductModel();
		
		//Attempts to query database for product
		try
		{
			//Binding data and getting result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);

			//Populating the found data if a valid result is returned
			while(srs.next())
				product = new ProductModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("DESCRIPTION"), srs.getString("GENRE"));
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
		
		//Returning a created product to the business service
		return product;
	}

	/**
	 * Returns a product back to the business service if that object is found in the database
	 * @param product A product to be searched for in the database. (This implementation uses the Name and Genre of the product as relevant criteria)
	 * @return Product Any product found using the criteria provided
	 */
	@Override
	public ProductModel findBy(ProductModel product) 
	{
		//SQL statement
		String sql = "SELECT * FROM springCLC.PRODUCTS WHERE NAME = ? AND GENRE = ?";
		
		//Creates a default product to be returned no matter what
		ProductModel foundProduct = new ProductModel();
		
		//Attempting to find a product in the database
		try
		{
			//Binding data and getting a result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, product.getName(), product.getGenre());
			
			//Populating the found product if a valid result is returned
			while(srs.next())
				foundProduct = new ProductModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("DESCRIPTION"), srs.getString("GENRE"));
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
		
		//Returning result regardless of whether or not is is populated with valid data
		return foundProduct;
	}

	/**
	 * Method to add a product to the database of products
	 * @param t The product to be added
	 * @return Whether or not the product was successfully added
	 */
	@Override
	public int create(ProductModel product) 
	{
		// Rows to be returned regardless of query result
		int rows= 0;
		
		//Sql query
		String sql = "INSERT INTO springCLC.PRODUCTS(USERS_ID, NAME, DESCRIPTION, GENRE) VALUES (?, ?, ?, ?)";

		//Attempts to add a product to the database
		try
		{
			//Binding variables as prepared statement
			rows = jdbcTemplateObject.update(sql, product.getUserID(), product.getName(), product.getDescription(), product.getGenre());
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}

		//Returns number of rows to the database
		return rows;
	}

	/**
	 * A function that allows products to be edited
	 * @param t product to be edited
	 */
	@Override
	public int update(ProductModel t) 
	{
		//SQL query
		String sql = "UPDATE PRODUCTS SET NAME = ?, DESCRIPTION = ?, GENRE = ? WHERE ID = ?";

		//Attempts to update product in the database
		try
		{
			//Bind data as prepared statement, update, and return result
			return jdbcTemplateObject.update(sql, t.getName(), t.getDescription(), t.getGenre(), t.getID());
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
	}

	/**
	 * This function removes a product from the database
	 * @param t product to be removed
	 */
	@Override
	public int delete(ProductModel t) 
	{
		//SQL query
		String sql = "DELETE FROM PRODUCTS WHERE ID = ?";
		
		//Attempts to delete product from the database
		try
		{
			//Bind data as prepared statement, update, and return result
			return jdbcTemplateObject.update(sql, t.getID());
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e) 
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
	}

	/**
	 * This method sets the datasource to be used in this DAO, which is injected at runtime to allow for flexibility and security
	 * @param dataSource The datasource specified by the app configuration
	 */
	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	@Override
	public ProductModel findByString(String search) {
		// TODO Auto-generated method stub
		return null;
	}
}
