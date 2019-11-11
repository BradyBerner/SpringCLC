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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@SuppressWarnings("SpringMVCViewInspection")
@Controller
@RequestMapping("/library")
public class MusicController {

    //Class scoped business service to handle back-end logic
    private MusicBusinessInterface<AlbumModel, SongModel> musicService;

    /**
     * This function injects a user business service at runtime for use in this particular class
     * @param musicService A business service which handles any functions related to music
     */
    @Autowired
    private void setMusicService(MusicBusinessInterface<AlbumModel, SongModel> musicService){
        this.musicService = musicService;
    }

    /**
     * This function gets all albums in the database that have the current user's ID as their foreign key and returns
     * the user to the library view
     * @param session Session variable to get the current user's ID from the principal stored in the session
     * @return Returns a ModelAndView with the view being directed towards the library and the Model being a list of AlbumModels
     */
    @RequestMapping("/")
    public ModelAndView displayLibrary(HttpSession session){

        //Initialise model and view for library and gives an empty album model for adding new albums
        ModelAndView mav = new ModelAndView("library", "album", new AlbumModel());

        try{
            //Attempts to get all the albums in the database for that user's library
            mav.addObject("library", musicService.findAllAlbumsByUser(((Principal)session.getAttribute("principal")).getID()));
            //Returns to the library page with all the user's albums and the empty album model in case the user wants to add an album
            return mav;
        } catch (ItemNotFoundException e){
            //Returns error if there isn't currently any albums in the user's library
            mav.addObject("message", new MessageModel("You don't currently have any items in your library", 0));
            return mav;
        } catch (Exception e){
            //Returns user to the main page if an unknown error occurs
            return new ModelAndView("main", "message", new MessageModel("There was an error retrieving your library", 0));
        }
    }

    /**
     * This function gets a single AlbumModel from the database using the ID passed to it
     * @param ID This is the ID of the Album that is to be retrieved from the database
     * @return Will return a ModelAndView displaying the album or will return to the error page if an error was encountered
     */
    @RequestMapping(value = "/viewAlbum", method = RequestMethod.GET)
    public ModelAndView displayAlbum(@RequestParam int ID){
        try{
            //Creates a new ModelAndView that leads the user back to the albumView page with the album obtained from the database
            ModelAndView mav = new ModelAndView("albumView", "album", musicService.findAlbumByID(ID));
            //Adds an empty song model object to the ModelAndView so the user can add a new song to the album
            mav.addObject("song", new SongModel());
            return mav;
        } catch (ItemNotFoundException e){
            //Returns the user to the main page if the album they're attempting to view isn't in the database
            return new ModelAndView("main", "message", new MessageModel("Looks like we couldn't find your album", 0));
        } catch (Exception e){
            //Returns the user to the main page if an unknown error occurs
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occured", 0));
        }
    }

    /**
     * This function attempts to add a new album to the database it first checks to see if the input has any errors if it
     * does then the function creates a new ModelAndView containing the album with errors, the AlbumModel list that represents
     * the users library, and a empty string named error to let the view know to display the modal. Otherwise on success
     * the function returns to the library view with the new album included.
     * @param album The AlbumModel to be added into the database
     * @param result The variable that will store any validation errors
     * @param session A variable that allows us to get the principal object out of the session to get the user's ID
     * @return A ModelAndView containing the updated library and directing to the library page
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ModelAndView addAlbum(@Valid @ModelAttribute("album") AlbumModel album, BindingResult result, HttpSession session){

        //Checks for errors
        if(result.hasErrors()){
            //Creates a new ModelAndView object that directs to the library and stores the AlbumModel with errors
            ModelAndView mav = new ModelAndView("library","album", album);
            try {
                //Tries to get the library from the database but should get it from the cache instead
                mav.addObject("library", musicService.findAllAlbumsByUser(((Principal)session.getAttribute("principal")).getID()));
            } catch (ItemNotFoundException e) {
                //Adds a message to the ModelAndView to let the user know their library is empty
                mav.addObject("message", new MessageModel("You don't currently have any items in your library", 0));
            }

            //Empty string that will be returned to the library view to let the page know that it needs to auto open the modal to display errors
            mav.addObject("error", "");
            //Returns the completed ModelAndView
            return mav;
        }

        try
        {
            //Sets the AlbumModel's foreign key to the current user's ID
            album.setUserID(((Principal)session.getAttribute("principal")).getID());
            //Attempts to add persist an album
            if(musicService.addAlbum(album))
            {
                //Returns success message
                return displayLibrary(session);
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

    /**
     * This method is for editing an album. It takes in the album model that the user passes to it through a form checks
     * to see if it has any errors and if so returns the user back to the form. Otherwise it attempts to proceed to persist
     * the edits in the database and handles any exceptions that are thrown
     * @param album The edited instance of the album model the user wants to edit
     * @param result The variable to store whether or not there are any validation errors
     * @return Returns a ModelAndView that will take the user to an error page, or back to the page they came from
     */
    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public ModelAndView editAlbum(@Valid @ModelAttribute("album") AlbumModel album, BindingResult result){
        //Checks for errors
        if(result.hasErrors()){
            //Creates a new ModelAndView object leading back to the albumView page with the album with errors
            ModelAndView mav = new ModelAndView("albumView", "album", album);
            //Adds an empty song model for adding new songs
            mav.addObject("song", new SongModel());
            //Adds an error string for opening the modal where the error occurred upon returning to the albumView page
            mav.addObject("error", "editAlbum");
            return mav;
        }

        try{
            //Checks to see if the album was actually edited
            if(musicService.editAlbumInfo(album) > 0){
                //Returns the user back to the albumView page
                return displayAlbum(album.getID());
            } else {
                //Returns the user to the main page letting them know there was an issue updating the album
                return new ModelAndView("main", "message", new MessageModel("There was a problem updating your Album.", 0));
            }
        } catch (ItemNotFoundException e) {
            //Returns the user to the main page letting them know the album they tried to edit wasn't found in the database
            return new ModelAndView("main", "message", new MessageModel("We couldn't seem to find the album that you're trying to edit.", 0));
        } catch (Exception e){
            //Returns the user to the main page if an unknown error occurs
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    /**
     * This method is for removing albums from the database. If the album is successfully removed then they user is returned
     * back to their library. If there is an error then the user is routed back to the main page and shown an appropriate error message
     * @param album The album model containing the album ID of the album to be deleted
     * @param session The session to get the current user's ID from
     * @return A ModelAndView object directing the user back to the appropriate page with the appropriate objects contained within
     */
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteAlbum(@ModelAttribute("album") AlbumModel album, HttpSession session){
        try{
            //Checks to see if the album was successfully deleted
            if(musicService.removeAlbum(album) > 0){
                //Returns the user to their library page when the album has been successfully deleted
                return displayLibrary(session);
            } else {
                //Returns the user to the main page with an appropriate error message when the user's album wasn't properly deleted
                return new ModelAndView("main", "message", new MessageModel("There was a problem deleting your Album.", 0));
            }
        } catch (ItemNotFoundException e) {
            //Returns the user to the main page with an appropriate error message letting the user know the album they're trying to delete couldn't be found
            return new ModelAndView("main", "message", new MessageModel("We couldn't seem to find the album that you're trying to delete.", 0));
        } catch (Exception e){
            //Returns the user to the main page and lets them know that an unknown error has occured
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    /**
     * This method is for adding a new song to the database. It first checks to see that the song's attributes are valid
     * in the event that they are not it returns the user back to the albumView with the errors. Otherwise the song is added
     * to the database unless there is an error in which case the user is returned back to the main page with an
     * appropriate error message.
     * @param song The song model to be added to the database
     * @param result Variable for storing any errors with the song model found
     * @return A ModelAndView object leading the user back to the appropriate page with the necessary model objects
     */
    @RequestMapping(path = "/addSong", method = RequestMethod.POST)
    public ModelAndView addSong(@Valid @ModelAttribute("song") SongModel song, BindingResult result){
        //Checks for any errors in the song model
        if(result.hasErrors()){
            //Creates a new ModelAndView object that routes the user back to the albumView page with the song model with errors
            ModelAndView mav = new ModelAndView("albumView", "song", song);
            try{
                //Adds the album model the song is a part of to the ModelAndView
                mav.addObject("album", musicService.findAlbumByID(song.getAlbumID()));
            } catch (ItemNotFoundException e) {
                //Returns the user to the main page with an error message in the event the song belongs to an album that doesn't exist
                return new ModelAndView("main", "message", new MessageModel("Looks like we couldn't find your album", 0));
            }

            //Adds an error message for opening the appropriate modal upon returning to the albumView page
            mav.addObject("error", "addSong");
            return mav;
        }

        try{
            //Attempts to add the song to the database and checks whether or not it was successfully added
            if(musicService.addSong(song)) {
                //Returns the user to the albumView page for the album that the song added is a part of
                return new ModelAndView("albumView", "album", musicService.findAlbumByID(song.getAlbumID()));
            } else {
                //Returns the user to the main page letting them know that there was an issue storing their song in the database
                return new ModelAndView("main", "message", new MessageModel("There was a problem creating your song.", 0));
            }
        } catch (ItemAlreadyExistsException e) {
            //Returns the user to the main page letting them know that an identical song already exists in the database
            return new ModelAndView("main", "message", new MessageModel("This song already exists in our records.", 0));
        } catch (ItemNotFoundException e) {
            //Returns the user to the main page letting them know that the album they were trying to add the song to couldn't be found in the database
            return new ModelAndView("main", "message", new MessageModel("It looks like there may have been an issue with the album you're trying to add a song to", 0));
        } catch (Exception e){
            //Returns the user to the main page letting them know that an unknown error has occured
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    /**
     * This method is for editing a song that is already persisted in the database. It first checks to see if there are
     * any errors with the edited song model and if there are then the user is directed back to the albumView page that
     * they came from. Otherwise the edits to the song are persisted to the database. In the event that they are successfully
     * persisted then the user is returned back to the albumView page that song is a part of otherwise the user is returned
     * to the main page with an appropriate error message.
     * @param song The edited song model that the user wants to persist to the database
     * @param result The object that holds whether or not there were any validation errors on the song model
     * @return A ModelAndView directing the user to the appropriate page with all the appropriate model object contained within
     */
    @RequestMapping(path = "/editSong", method = RequestMethod.POST)
    public ModelAndView editSong(@Valid @ModelAttribute("song") SongModel song, BindingResult result){
        //Checks to see if the song model is valid
        if (result.hasErrors()) {
            //Creates a new ModelAndView object directing the user back to the albumView page they came from with the song model with errors
            ModelAndView mav = new ModelAndView("albumView", "song", song);
            try{
                //Tries to get the album that the song is from and add it to the ModelAndView
                mav.addObject("album", musicService.findAlbumByID(song.getAlbumID()));
            } catch (ItemNotFoundException e){
                //Returns the user to the main page with an error message if the album the song is a part of can't be found
                return new ModelAndView("main", "message", new MessageModel("Looks like we couldn't find your album", 0));
            }

            //Adds an error to the ModelAndView that lets the page know which modal to open to display the errors
            mav.addObject("error", "editSong"+song.getID());
            return mav;
        }

        try{
            //Attempts to persist the updated song model to the database and checks for success
            if(musicService.editTrackInfo(song) > 0){
                //Returns the user back to the albumView page for the album that the current song is a part of
                return new ModelAndView("albumView", "album", musicService.findAlbumByID(song.getAlbumID()));
            } else {
                //Returns the user back to the main page letting them know there was a problem persisting their updates to the song
                return new ModelAndView("main", "message", new MessageModel("There was a problem editing your song.", 0));
            }
        } catch (ItemNotFoundException e) {
            //Returns the user to the main page letting them know the song they were trying to edit couldn't be found in the database
            return new ModelAndView("main", "message", new MessageModel("It looks like we can't find the song you're trying to edit", 0));
        } catch (Exception e){
            //Returns the user to the main page letting them know that an unknown error has occured
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    /**
     * This method is for removing song models that have been persisted in the database. It attempts to remove the song
     * in the event of a success it returns the user back to the albumView page they came from. In the event of any kind
     * of error the user is instead returned back to the main page with an appropriate error message.
     * @param song The song model for the song that the user wishes to remove from the database
     * @return A ModelAndView taking the user to the appropriate page with all the necessary objects contained within
     */
    @RequestMapping(path = "/deleteSong", method = RequestMethod.POST)
    public ModelAndView deleteSong(@ModelAttribute("song") SongModel song){
        try{
            //Tries to remove the song from the database and checks to for success
            if(musicService.removeTrack(song) > 0){
                //Returns the user to the albumView for the album that the removed song was a part of
                return new ModelAndView("albumView", "album", musicService.findAlbumByID(song.getAlbumID()));
            } else {
                //Returns the user to the main page with an errors message letting them know the song couldn't be deleted
                return new ModelAndView("main", "message", new MessageModel("There was a problem deleting your song.", 0));
            }
        } catch (ItemNotFoundException e) {
            //Returns the user to the main page with an error message letting them know the song they're trying to delete couldn't be found
            return new ModelAndView("main", "message", new MessageModel("It looks like we can't find the song you're trying to delete", 0));
        } catch (Exception e){
            //Returns the user to the main page with an error message letting them know an unknown error occurred
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }
}
