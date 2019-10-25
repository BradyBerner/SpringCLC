package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import com.gcu.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

/**
 * Data service that handles database persistence logic regarding products
 */
public class ProductDataService implements DataAccessInterface<ProductModel> {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/**
	 * A method that returns a list of every product in the database
	 * @return A list of all products currently in the database
	 */
	@Override
	public List<ProductModel> findAll() {

		String sql = "SELECT * FROM springCLC.PRODUCTS";
		ArrayList<ProductModel> products = new ArrayList<>();

		try{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);

			while(srs.next()){
				products.add(new ProductModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("DESCRIPTION"), srs.getString("GENRE")));
			}
		} catch (Exception e){
			e.printStackTrace();
		}

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

		String sql = "SELECT * FROM springCLC.PRODUCTS WHERE ID = ?";
		ProductModel product = null;

		try{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);

			while(srs.next())
				product = new ProductModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("DESCRIPTION"), srs.getString("GENRE"));
		} catch (Exception e){
			e.printStackTrace();
		}

		return product;
	}

	@Override
	public ProductModel findBy(ProductModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to add a product to the database of products
	 * @param t The product to be added
	 * @return Whether or not the product was successfully added
	 */
	@Override
	public boolean create(ProductModel t) {

		String sql = "INSERT INTO springCLC.PRODUCTS(ID, USERS_ID, NAME, DESCRIPTION, GENRE) VALUES (?, ?, ?, ?, ?)";

		try{
			int rows = jdbcTemplateObject.update(sql, t.getID(), t.getUserID(), t.getName(), t.getDescription(), t.getGenre());

			return rows == 1;
		} catch (Exception e){
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * A function that allows products to be edited
	 * @param t product to be edited
	 */
	@Override
	public int update(ProductModel t) {

		String sql = "UPDATE PRODUCTS SET NAME = ?, DESCRIPTION = ?, GENRE = ? WHERE ID = ?";

		try{
			return jdbcTemplateObject.update(sql, t.getName(), t.getDescription(), t.getGenre(), t.getID());
		} catch (Exception e){
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * This function removes a product from the database
	 * @param t product to be removed
	 */
	@Override
	public int delete(ProductModel t) {

		String sql = "DELETE FROM PRODUCTS WHERE ID = ?";

		try{
			return jdbcTemplateObject.update(sql, t.getID());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Autowired
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
}
