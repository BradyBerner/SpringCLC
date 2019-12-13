package com.gcu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcu.business.MusicBusinessInterface;
import com.gcu.model.AlbumModel;
import com.gcu.model.SongModel;
import com.gcu.utility.ItemNotFoundException;

/**
 * REST controller that currently handles CRUD methods regarding products
 */
@RestController
@RequestMapping("/albumRest")
public class MusicRestService 
{
	//Class scoped business service to handle back-end logic
	private MusicBusinessInterface<AlbumModel,SongModel> musicService;
			
	/**
	* This function injects a user business service at runtime for use in this particular class
	* @param musicService A business service which handles any functions related to login
	*/
	@Autowired
	public void setProductBusinessService(MusicBusinessInterface<AlbumModel,SongModel> musicService)
	{
		this.musicService=musicService;
	}

	/**
	 * REST method that returns a list of all user in the database
	 * @return Returns a list of albums that are in the user's library
	 */
	@GetMapping("/albums")
	public List<AlbumModel> getAlbums()
	{
		try 
		{
			//Not currently supported
			return musicService.findAllAlbumsByUser(1);
		} 
		catch (ItemNotFoundException e) 
		{
			return new ArrayList<AlbumModel>();
		}
	}

}
