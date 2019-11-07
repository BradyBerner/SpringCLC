package com.gcu.business;

import java.util.List;

import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;

/**
 * Interface class for any business service that will handle products. Every product SpringBean must contain CRUD methods at a minimum
 *
 */
public interface MusicBusinessInterface<T, S>
{
	/**
	 * Method to add an album to the database
	 * @param product The album to be added
	 * @return boolean Whether or not the album was successfully added
	 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied album already exists in the database, and duplicates are not allowed
	 */
	public boolean addAlbum(T album) throws ItemAlreadyExistsException;
	
	/**
	 * Method to add a song to the database of products
	 * @param product The song to be added
	 * @return boolean Whether or not the song was successfully added
	 * @throws ItemAlreadyExistsException This exception is thrown in the event that the supplied song already exists in the database, and duplicates are not allowed
	 * @throws ItemNotFoundException This exception is thrown in the event that a necessary item is not found
	 */
	public boolean addSong(S song) throws ItemAlreadyExistsException, ItemNotFoundException;
	
	/**
	 * Method to find a single album using that product's ID
	 * @param id The ID of the album being searched for
	 * @return SongModel The album found. A -1 will be returned for ID in the event that nothing is found
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public T findAlbumByID(int id) throws ItemNotFoundException;

	/**
	 * Returns a list of albums from the database with a certain ID, whether that is the product's primary key or a foreign key.
	 * @param id The ID to search by
	 * @return List<T> The list of all results with the searched ID.
	 * @throws ItemNotFoundException This exception is thrown in the event that no item is found in the database
	 */
	public List<T> findAllAlbumsByUser(int id) throws ItemNotFoundException;
	
	/**
	 * A function that allows albums to be edited
	 * @param album An album to be edited
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public boolean editAlbumInfo(T album) throws ItemNotFoundException;
	
	/**
	 * A function that allows songs to be edited
	 * @param album A song to be edited
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public boolean editTrackInfo(S song) throws ItemNotFoundException;
	
	/**
	 * This function removes an album from the database
	 * @param album An album to be removed
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public boolean removeAlbum(T album) throws ItemNotFoundException;
	
	/**
	 * This function removes a song from the database
	 * @param song A song to be removed
	 * @return boolean Success/Failure depending on the success of the operation
	 * @throws ItemNotFoundException This exception is thrown in the event that no item matching the parameters is found in the database
	 */
	public boolean removeTrack(S song) throws ItemNotFoundException;
}
