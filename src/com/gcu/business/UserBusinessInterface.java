package com.gcu.business;

import java.util.List;

import com.gcu.model.CredentialsModel;
import com.gcu.model.UserModel;

public interface UserBusinessInterface 
{
		// Creation method
		public boolean register(UserModel user);
		//Login method
		public int authenticate(CredentialsModel cred);
		//Single read method
		public UserModel findByID(int id);
		//Read all method
		public List<UserModel> findAll();
		//Update method
		public boolean editUser(UserModel user);
		// Deletion method
		public boolean remove(UserModel user);
}
