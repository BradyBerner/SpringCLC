package com.gcu.business;

import java.util.List;
import com.gcu.data.DataAccessInterface;
import com.gcu.model.AlbumModel;
import com.gcu.model.SongModel;
import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;


/**
 * Implementation of a product business service that can handle business logic related to products
 */
public class MusicBusinessService implements MusicBusinessInterface<AlbumModel,SongModel> 
{
	//Class scoped data service injected at runtime to handle songs
	DataAccessInterface<SongModel> trackService;
	
	//Class scoped data service injected at runtime to handle albums
	DataAccessInterface<AlbumModel> albumService;
	
	/**
	 * This method injects a data Service at runtime to be used within the business service
	 * @param trackService The data service to be injected
	 */
	public void setTrackDataService(DataAccessInterface<SongModel> trackService)
	{
		this.trackService=trackService;
	}
	
	/**
	 * This method injects a data Service at runtime to be used within the business service
	 * @param albumService The data service to be injected
	 */
	public void setAlbumDataService(DataAccessInterface<AlbumModel> albumService)
	{
		this.albumService=albumService;
	}
	
	
	/**
	 * Method to add an album to the database
	 * @param album The album to be added
	 * @return boolean Whether or not the album was successfully added
	 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied album already exists in the database, and duplicates are not allowed
	 */
	@CacheEvict(value = "library", allEntries = true)
	public boolean addAlbum(AlbumModel album) throws ItemAlreadyExistsException
	{
		//First Checks to see if the product already exists in the database. In the event that it does, returns an exception to the user
		if(albumService.findBy(album).getID()>0)
			throw new ItemAlreadyExistsException();
		
		//Attempts to create a product in the database
		if(albumService.create(album)>0)
			return true;
		else
			return false;
	}
	
	/**
	 * Method to add a song to the database of products
	 * @param song The song to be added
	 * @return boolean Whether or not the song was successfully added
	 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied song already exists in the database, and duplicates are not allowed
	 * @throws ItemNotFoundException This exception is thrown in the event that the user is attempting to add a track to an album that does not exist
	 */
	@Caching(evict = {
			@CacheEvict(value = "albums", key = "#song.albumID"),
			@CacheEvict(value = "tracks", key = "#song.albumID")
	})
	public boolean addSong(SongModel song) throws ItemAlreadyExistsException, ItemNotFoundException
	{
		//Checks to see if the track is associated with a valid album. If it is not, returns an exception to the user
		if(albumService.findByID(song.getAlbumID()).getID()<1)
			throw new ItemNotFoundException();
		//Checks to see if the product already exists in the database. In the event that it does, returns an exception to the user
		if(trackService.findBy(song).getID()>0)
			throw new ItemAlreadyExistsException();
		
		//Attempts to create a product in the database
		if(trackService.create(song)>0)
			return true;
		else
			return false;
	}
	
	/**
	 * Method to find a single album using that product's ID
	 * @param id The ID of the album being searched for
	 * @return AlbumModel The album found. A -1 will be returned for ID in the event that nothing is found
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Cacheable(value = "albums")
	public AlbumModel findAlbumByID(int id) throws ItemNotFoundException
	{
		AlbumModel album = albumService.findByID(id);

		if(album.getID() != 0) {
			album.setTracks(trackService.findAllWithID(album.getID()));
		} else {
			throw new ItemNotFoundException();
		}

		return album;
	}

	/**
	 * Returns a list of albums from the database with a certain user's ID
	 * @param userID The ID to search by
	 * @return List<AlbumModel> The list of all results with the searched ID.
	 * @throws ItemNotFoundException This exception is thrown in the event that no item is found in the database
	 */
	@Cacheable(value = "library")
	public List<AlbumModel> findAllAlbumsByUser(int userID) throws ItemNotFoundException
	{
		//Populating an arrayList from DAO for all this user's albums
		List<AlbumModel> albums = albumService.findAllWithID(userID);
		
		//Checking for valid output
		if(albums.size()>0)
		{
			//If any albums are found, populates the albums with the tracks found			
			for(AlbumModel album: albums)
			{
				//Calls the database for an array list of associated tracks and populates the album with it
				album.setTracks(trackService.findAllWithID(album.getID()));
			}
			
			//Returning list of albums to the front end
			return albums;
		}
		//Checking for empty return value and returning error
		else
		{
			throw new ItemNotFoundException();
		}
	}
	
	/**
	 * A function that allows albums to be edited
	 * @param album An album to be edited
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Caching(evict = {
			@CacheEvict(value = "albums", key = "#album.ID"),
			@CacheEvict(value = "library", allEntries = true)
	})
	public int editAlbumInfo(AlbumModel album) throws ItemNotFoundException
	{
		return albumService.update(album);
	}
	
	/**
	 * A function that allows songs to be edited
	 * @param song A song to be edited
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Caching(evict = {
			@CacheEvict(value = "albums", key = "#song.albumID"),
			@CacheEvict(value = "tracks", key = "#song.albumID")
	})
	public int editTrackInfo(SongModel song) throws ItemNotFoundException
	{
		return trackService.update(song);
	}
	
	/**
	 * This function removes an album from the database
	 * @param album An album to be removed
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@CacheEvict(value = "library", allEntries = true)
	public int removeAlbum(AlbumModel album) throws ItemNotFoundException
	{
		return albumService.delete(album);
	}
	
	/**
	 * This function removes a song from the database
	 * @param song A song to be removed
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	@Caching(evict = {
			@CacheEvict(value = "albums", key = "#song.albumID"),
			@CacheEvict(value = "tracks", key = "#song.albumID")
	})
	public int removeTrack(SongModel song) throws ItemNotFoundException
	{
		return trackService.delete(song);
	}
}