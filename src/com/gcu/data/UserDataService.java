package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import com.gcu.model.UserModel;
import com.gcu.utility.DatabaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

/**
 * Data access service that handles database persistence logic regarding users
 */
public class UserDataService implements DataAccessInterface<UserModel> {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	/**
	 * A method that finds a user (either by an entire model or some relevant attributes) in the database and returns it.
	 * In this case, we search for a user by their credentials in order to authenticate them upon login
	 * @param user The user we are looking for
	 * @return The user, if found
	 */
	@Override
	public UserModel findBy(UserModel user) 
	{
		//Sql query
		String sql = "SELECT * FROM springCLC.USERS WHERE USERNAME = ? AND PASSWORD = ?";
		//Creating a new user to be returned regardless of query result
		UserModel foundUser = new UserModel();

		//Attempts to find a user in the database using the supplied credentials
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, user.getCredentials().getUsername(), user.getCredentials().getPassword());
			
			//If a result is found, populates a user with valid information. Otherwise the default user is returned to the business service
			if(srs.next())
			{
				foundUser = new UserModel(srs.getInt("ID"), srs.getString("FIRSTNAME"), srs.getString("LASTNAME"), srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("EMAIL"), srs.getString("PHONENUMBER"), srs.getInt("ROLE"), srs.getInt("STATUS"));
			}
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
		
		//Returning the found user to the business service
		return foundUser;
	}
	
	/**
	 * Returns a list of all users found in the database
	 *@return Returns a list of all users
	 */
	@Override
	public List<UserModel> findAll() 
	{
		//Sql query
		String sql = "SELECT * FROM springCLC.USERS";
		//An arraylist to be returned to the business service regardless of the query result
		ArrayList<UserModel> users = new ArrayList<>();

		//Attempts to find users in the database
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			
			//If results are found, populates an arraylist with valid information. Otherwise the default list is returned to the business service
			while(srs.next()){
				users.add(new UserModel(srs.getInt("ID"), srs.getString("FIRSTNAME"), srs.getString("LASTNAME"), srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("EMAIL"), srs.getString("PHONENUMBER"), srs.getInt("ROLE"), srs.getInt("STATUS")));
			}
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
		
		//Returning the list of users to the business service
		return users;
	}

	/**
	 * Returns a user found with a certain ID
	 * @param id The ID of the user being searched for
	 * @return A user found with a certain ID, or a blank user object
	 */
	@Override
	public UserModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to add a user to the database
	 * @param user A user to be registered
	 * @return int Returns the number of affected rows in the database
	 */
	@Override
	public int create(UserModel user) 
	{
		
		// Rows to be returned regardless of query result
		int rows= 0;
		
		//Sql query
		String sql = "INSERT INTO springCLC.USERS(ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, EMAIL, PHONENUMBER, ROLE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		//Attempts to add a product to the database
		try
		{
			//Binds data and sets result to the variable to be returned to the business service
			rows = jdbcTemplateObject.update(sql, user.getID(), user.getFirstName(), user.getLastName(), user.getCredentials().getUsername(), user.getCredentials().getPassword(), user.getEmail(), user.getPhoneNumber(), user.getRole(), user.getStatus());
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
	 * A function that allows user information to be edited
	 * @param t The user to be edited
	 * @return int Returns the number of affected rows in the database
	 */
	@Override
	public int update(UserModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A function to remove a user from the database
	 * @param t The user to be deleted
	 * @return int Returns the number of affected rows in the database 
	 */
	@Override
	public int delete(UserModel t) {
		// TODO Auto-generated method stub
		return 0;
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
	 * Returns an object back to the business service if that object is found using a certain string
	 * @param search A String search used to find a relevant object
	 * @return T The object found with this search
	 */
	@Override
	public UserModel findByString(String search) 
	{
		//SQL statement
		String sql = "SELECT * FROM springCLC.USERS WHERE USERNAME = ?";
		
		//User to be returned regardless of query result
		UserModel foundUser = new UserModel();

		//Attmepts to find the user in the database
		try
		{
			//Binding data and getting a result
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, search);

			//If a valid result is returned, populates the user model
			while(srs.next())
				foundUser = new UserModel(srs.getInt("ID"), srs.getString("FIRSTNAME"), srs.getString("LASTNAME"), srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("EMAIL"), srs.getString("PHONENUMBER"), srs.getInt("ROLE"), srs.getInt("STATUS"));
		} 
		//Exception thrown by the JDBCTemplate object in case there is an issue with a query/update
		catch (DataAccessException e)
		{
			e.printStackTrace();
			throw new DatabaseException();
		}
		
		//Returns the user model to the business
		return foundUser;
	}
}
