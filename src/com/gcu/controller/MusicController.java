package com.gcu.controller;

import com.gcu.business.MusicBusinessInterface;
import com.gcu.model.AlbumModel;
import com.gcu.model.MessageModel;
import com.gcu.model.Principal;
import com.gcu.model.SongModel;
import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * This controller handles all user actions concerning music
 */
@Controller
@RequestMapping("/music")
public class MusicController {

	//Class scoped business service to handle back-end logic
    private MusicBusinessInterface<AlbumModel,SongModel> musicService;
		
	/**
	* This function injects a user business service at runtime for use in this particular class
	* @param musicService A business service which handles any functions related to music
	*/
	@Autowired
	public void setMusicBusinessService(MusicBusinessInterface<AlbumModel,SongModel> musicService)
	{
			this.musicService=musicService;
	}
		
	/**
	 * This method displays the form to create an album
	 * @return A view displaying the album creation form
	 */
    @RequestMapping(path = "/createAlbum", method = RequestMethod.GET)
    public ModelAndView displayCreateAlbum()
    {
        return new ModelAndView("albumCreationPortal", "album", new AlbumModel());
    }

    /**
     * This method displays all the user's albums
     * @param session The session is gathered in order to fetch the user ID from the principle
     * @return A view displaying all albums associated with this user
     */
    @RequestMapping(path = "/library", method = RequestMethod.GET)
    public ModelAndView displayLibrary(HttpSession session)
    {
    	//Attempts to retrieve a user library
	    try
	    {
            return new ModelAndView("library", "library", musicService.findAllAlbumsByUser(((Principal)session.getAttribute("principal")).getID()));
        } 
	    //Returns message if nothing is found
	    catch(ItemNotFoundException e)
	    {
	    	return new ModelAndView("library", "library", new MessageModel("You don't currently have any items in your library", 0));
	    }
	    //Catching unknown errors
	    catch (Exception e){
            return new ModelAndView("main", "message", new MessageModel("There was an error retrieving your library", 0));
        }
    }

    /**
     * This method attempt to create an album supplied by the user
     * @param album The album the user is attempting to create
     * @param result The result of the operation from the front end. Used to check for validation errors
     * @param session The session is gathered in order to fetch the user ID from the principle 
     * @return A View depending on the result of the operation
     */
    @RequestMapping(path = "/doCreateAlbum", method = RequestMethod.POST)
    public ModelAndView createAlbum(@Valid @ModelAttribute("music") AlbumModel album, BindingResult result, HttpSession session){
    	
    	//returns to album creation form in the event of validation errors
        if(result.hasErrors())
        {
            return new ModelAndView("albumCreationPortal", "album", album);
        }
        
        //Sets the user id of the album using the logged in user ID stored in the session
        album.setUserID(((Principal)session.getAttribute("principal")).getID());
        
        //Attempts to persist the album
        try
        {
        	//Attempts to add persist an album
        	if(musicService.addAlbum(album))
        	{
        		///Returns success message
        		String m = "New Album Successfully Created";
            	return new ModelAndView("main", "message", new MessageModel(m, 1));
        	}
        	//Returns this message if by some miracle the back end bypasses all exceptions only to return a false value in album creation
        	else
            	return new ModelAndView("main", "message", new MessageModel("There was a problem creating your Album.", 0));
        }
        
        catch(ItemAlreadyExistsException e)
        {
        	return new ModelAndView("main", "message", new MessageModel("This album already exists in our records.", 0));
        }
        catch(Exception e)
        {
        	return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }
}
