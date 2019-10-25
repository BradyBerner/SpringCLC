package com.gcu.data;

import java.util.ArrayList;
import java.util.List;

import com.gcu.model.CredentialsModel;
import com.gcu.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

/**
 * Data access service that handles database persistence logic regarding users
 *
 */
public class UserDataService implements DataAccessInterface<UserModel> {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	/**
	 * Method that handles attempted user login
	 * @param credentials The credentials a user is attempting to log in with
	 * @return The user's ID upon a success. -1 upon a failure
	 */
	public UserModel findByCredential(CredentialsModel credentials) 
	{
		String sql = "SELECT * FROM springCLC.USERS WHERE USERNAME = ? AND PASSWORD = ?";
		UserModel user = new UserModel();

		try{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, credentials.getUsername(), credentials.getPassword());

			while(srs.next()){
				user = new UserModel(srs.getInt("ID"), srs.getString("FIRSTNAME"), srs.getString("LASTNAME"), srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("EMAIL"), srs.getString("PHONENUMBER"), srs.getInt("ROLE"), srs.getInt("STATUS"));
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return user;
	}
	
	/**
	 * Returns a list of all users found in the database
	 *@return Returns a list of all users
	 */
	@Override
	public List<UserModel> findAll() 
	{
		String sql = "SELECT * FROM springCLC.USERS";
		ArrayList<UserModel> users = new ArrayList<>();

		try{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);

			while(srs.next()){
				users.add(new UserModel(srs.getInt("ID"), srs.getString("FIRSTNAME"), srs.getString("LASTNAME"), srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("EMAIL"), srs.getString("PHONENUMBER"), srs.getInt("ROLE"), srs.getInt("STATUS")));
			}
		} catch (Exception e){
			e.printStackTrace();
		}

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
	 * A method that finds a user in the database and returns it. Maybe useful, probably not. Who knows.
	 * @param t The user we are looking for
	 * @return The user, if found
	 */
	@Override
	public UserModel findBy(UserModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to add a user to the database
	 * @param t A user to be registered
	 * @return Returns a boolean stating success or failure of registration 
	 */
	@Override
	public boolean create(UserModel t) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * A function that allows user information to be edited
	 * @param t The user to be edited
	 */
	@Override
	public int update(UserModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * A function to remove a user from the database
	 * @ param user The user to be deleted 
	 */
	@Override
	public int delete(UserModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Autowired
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
}
