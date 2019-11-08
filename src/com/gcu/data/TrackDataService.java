package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import com.gcu.model.SongModel;
import com.gcu.utility.DatabaseException;
import com.gcu.utility.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

/**
 * Data service that handles database persistence logic regarding products
 */
public class TrackDataService implements DataAccessInterface<SongModel> 
{

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/**
	 * Not supported at this time as finding all albums regardless of user would be meaningless
	 */
	@Override
	public List<SongModel> findAll() 
	{
		throw new NotSupportedException();
	}
	
	/**
	 * Returns a list of albums from the database with a certain album ID
	 * @param id The ID to search by
	 * @return The list of all results with the searched ID.
	 */
	@Override
	public List<SongModel> findAllWithID(int id) 
	{
		//SQL Statement
		String sql = "SELECT * FROM springCLC.SONGS WHERE ALBUMS_ID = ?";

		//Creation of a blank list of tracks so that some data may be returned no matter what
		ArrayList<SongModel> tracks = new ArrayList<>();

		//Queries the database
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);

			while(srs.next()){
				tracks.add(new SongModel(srs.getInt("ID"), srs.getInt("ALBUMS_ID"), srs.getString("NAME"), srs.getString("ARTIST")));
			}
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e){
			e.printStackTrace();
			throw new DatabaseException();
		}
		
		
		//Returning list of tracks
		return tracks;
	}

	/**
	 * Method to find a single product using that product's ID
	 * @param id The ID of the product being searched for
	 * @return The product found. A -1 will be returned for ID in the event that nothing is found
	 */
	@Override
	public SongModel findByID(int id) 
	{
		//SQL Statement
		String sql = "SELECT * FROM springCLC.SONGS WHERE ID = ?";
		
		//Creating a default product to be returned no matter what
		SongModel product = new SongModel();
		
		//Attempts to query database for product
		try
		{
			//Binding data and getting result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);

			//Populating the found data if a valid result is returned
			while(srs.next())
				product = new SongModel(srs.getInt("ID"), srs.getInt("ALBUMS_ID"), srs.getString("NAME"), srs.getString("ARTIST"));
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
	 * Returns a song back to the business service if that song is found in the database
	 * @param product A song to be searched for in the database. (This implementation uses the Name and and the Album as relevant criteria)
	 * @return Product Any song found using the criteria provided
	 */
	@Override
	public SongModel findBy(SongModel product) 
	{
		//SQL statement
		String sql = "SELECT * FROM springCLC.SONGS WHERE NAME = ? AND ALBUMS_ID = ?";
		
		//Creates a default product to be returned no matter what
		SongModel foundProduct = new SongModel();
		
		//Attempting to find a product in the database
		try
		{
			//Binding data and getting a result set
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, product.getName(), product.getAlbumID());
			
			//Populating the found product if a valid result is returned
			while(srs.next())
				foundProduct = new SongModel(srs.getInt("ID"), srs.getInt("ALBUMS_ID"), srs.getString("NAME"), srs.getString("ARTIST"));
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
	 * Method to add a song to the database
	 * @param song The song to be added
	 * @return Whether or not the song was successfully added
	 */
	@Override
	public int create(SongModel song)
	{
		// Rows to be returned regardless of query result
		int rows= 0;
		
		//Sql query
		String sql = "INSERT INTO springCLC.SONGS(ALBUMS_ID, NAME, ARTIST) VALUES (?, ?, ?)";

		//Attempts to add a song to the database
		try
		{
			//Binding variables as prepared statement
			rows = jdbcTemplateObject.update(sql, song.getAlbumID(), song.getName(), song.getArtist());
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
	 * @param song  The updated song to be persisted
	 * @return int The number of affected rows
	 */
	@Override
	public int update(SongModel song) 
	{
		//SQL query
		String sql = "UPDATE SONGS SET NAME = ?, ARTIST = ? WHERE ID = ?";

		//Attempts to update product in the database
		try
		{
			//Bind data as prepared statement, update, and return result
			return jdbcTemplateObject.update(sql, song.getName(), song.getArtist(), song.getID());
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
	}

	/**
	 * This function removes a song from the database
	 * @param song product to be removed
	 */
	@Override
	public int delete(SongModel song) 
	{
		//SQL query
		String sql = "DELETE FROM springCLC.SONGS WHERE ID = ?";
		
		//Attempts to delete product from the database
		try
		{
			//Bind data as prepared statement, update, and return result
			return jdbcTemplateObject.update(sql, song.getID());
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

	/**
	 * This function is not currently supported as there is no reason to search the database for a specific song by a string at this time
	 */
	@Override
	public SongModel findByString(String search) {
		throw new NotSupportedException();
	}
}
