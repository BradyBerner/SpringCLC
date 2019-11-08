package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gcu.model.AlbumModel;
import com.gcu.utility.DatabaseException;
import com.gcu.utility.NotSupportedException;

/**
 * This class handles CRUD operations concerning an album in this application
 */
public class AlbumDataService implements DataAccessInterface<AlbumModel> 
{

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
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
	
	/**
	 * Not supported at this time as finding all albums regardless of user would be meaningless
	 */
	@Override
	public List<AlbumModel> findAll() {
		throw new NotSupportedException();
	}

	/**
	 * Returns a list of albums from the database with a certain user ID
	 * @param id The ID to search by
	 * @return The list of all results with the searched ID.
	 */
	@Override
	public List<AlbumModel> findAllWithID(int id) {
		//SQL Statement
		String sql = "SELECT * FROM springCLC.ALBUMS WHERE USERS_ID = ?";

		ArrayList<AlbumModel> albums = new ArrayList<>();

		//Queries the database for all albums associated with this user
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			
			//Adding every album found with this userID
			while(srs.next())
			{
				//Binding data and populating an arrayList with each album
				albums.add(new AlbumModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("ARTIST"), srs.getString("DESCRIPTION"), srs.getString("GENRE"),null));
			}
		}
		//Catching SQL Errors
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}

		return albums;
	}

	/**
	 * Method to find a single album using that album's ID
	 * @param id The ID of the album being searched for
	 * @return The album found. A -1 will be returned for ID in the event that nothing is found
	 */
	@Override
	public AlbumModel findByID(int id) 
	{
		//SQL Statement
		String sql = "SELECT * FROM springCLC.ALBUMS WHERE ID = ?";
		
		//Creating a default product to be returned no matter what
		AlbumModel product = new AlbumModel();
		
		//Attempts to query database for product
		try
		{
			//Binding data and getting result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);

			//Populating the found data if a valid result is returned
			while(srs.next())
				product = new AlbumModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("ARTIST"), srs.getString("DESCRIPTION"), srs.getString("GENRE"),null);
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
	 * Returns an album back to the business service if that album is found in the database
	 * @param product An album to be searched for in the database. (This implementation uses the Name and Artist of the product as relevant criteria)
	 * @return Product Any album found using the criteria provided
	 */
	@Override
	public AlbumModel findBy(AlbumModel product) 
	{
		//SQL statement
		String sql = "SELECT * FROM springCLC.ALBUMS WHERE NAME = ? AND ARTIST = ?";
		
		//Creates a default album to be returned no matter what
		AlbumModel foundProduct = new AlbumModel();
		
		//Attempting to find a album in the database
		try
		{
			//Binding data and getting a result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, product.getName(), product.getArtist());
			
			//Populating the found product if a valid result is returned
			while(srs.next())
				foundProduct = new AlbumModel(srs.getInt("ID"), srs.getInt("USERS_ID"), srs.getString("NAME"), srs.getString("ARTIST"),srs.getString("DESCRIPTION"), srs.getString("GENRE"),null);
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
	 * This function is not supported as there is currently no reason to find a specific album by a string
	 */
	@Override
	public AlbumModel findByString(String search) {
		throw new NotSupportedException();
	}

	/**
	 * Method to add an album to the database
	 * @param album The album to be added
	 * @return Whether or not the album was successfully added
	 */
	@Override
	@CacheEvict(value = "library", allEntries = true)
	public int create(AlbumModel album)
	{
		// Rows to be returned regardless of query result
		int rows= 0;
		
		//Sql query
		String sql = "INSERT INTO springCLC.ALBUMS(USERS_ID, NAME, ARTIST, DESCRIPTION, GENRE) VALUES (?, ?, ?, ?, ?)";

		//Attempts to add a product to the database
		try
		{
			//Binding variables as prepared statement
			rows = jdbcTemplateObject.update(sql, album.getUserID(), album.getName(), album.getArtist(), album.getDescription(), album.getGenre());
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
	 * A function that allows album information to be edited
	 * @param album The updated version of an album
	 * @return int A number signifying the number of affected rows
	 */
	@Override
	public int update(AlbumModel album) 
	{
		//SQL query
		String sql = "UPDATE springCLC.ALBUMS SET NAME = ?, ARTIST= ?, DESCRIPTION = ?, GENRE = ? WHERE ID = ?";

		//Attempts to update product in the database
		try
		{
			//Bind data as prepared statement, update, and return result
			return jdbcTemplateObject.update(sql, album.getName(), album.getArtist(), album.getDescription(), album.getGenre(), album.getID());
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
	 * @param album Album to be removed
	 */
	@Override
	public int delete(AlbumModel album) 
	{
		//SQL query
		String sql = "DELETE FROM springCLC.ALBUMS WHERE ID = ?";
		
		//Attempts to delete product from the database
		try
		{
			//Bind data as prepared statement, update, and return result
			return jdbcTemplateObject.update(sql, album.getID());
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e) 
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
	}

}
