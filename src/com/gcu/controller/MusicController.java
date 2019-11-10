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

        ModelAndView mav = new ModelAndView("library", "album", new AlbumModel());

        try{
            mav.addObject("library", musicService.findAllAlbumsByUser(((Principal)session.getAttribute("principal")).getID()));
            return mav;
        } catch (ItemNotFoundException e){
            mav.addObject("message", new MessageModel("You don't currently have any items in your library", 0));
            return mav;
        } catch (Exception e){
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
            ModelAndView mav = new ModelAndView("albumView", "album", musicService.findAlbumByID(ID));
            mav.addObject("song", new SongModel());
            return mav;
        } catch (ItemNotFoundException e){
            return new ModelAndView("main", "message", new MessageModel("Looks like we couldn't find your album", 0));
        } catch (Exception e){
            e.printStackTrace();
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
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView("albumView", "album", album);
            mav.addObject("song", new SongModel());
            mav.addObject("error", "editAlbum");
            return mav;
        }

        try{
            if(musicService.editAlbumInfo(album) > 0){
                return displayAlbum(album.getID());
            } else {
                return new ModelAndView("main", "message", new MessageModel("There was a problem updating your Album.", 0));
            }
        } catch (ItemNotFoundException e) {
            return new ModelAndView("main", "message", new MessageModel("We couldn't seem to find the album that you're trying to edit.", 0));
        } catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteAlbum(@ModelAttribute("album") AlbumModel album, HttpSession session){
        try{
            if(musicService.removeAlbum(album) > 0){
                return displayLibrary(session);
            } else {
                return new ModelAndView("main", "message", new MessageModel("There was a problem deleting your Album.", 0));
            }
        } catch (ItemNotFoundException e) {
            return new ModelAndView("main", "message", new MessageModel("We couldn't seem to find the album that you're trying to delete.", 0));
        } catch (Exception e){
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    @RequestMapping(path = "/addSong", method = RequestMethod.POST)
    public ModelAndView addSong(@Valid @ModelAttribute("song") SongModel song, BindingResult result){

        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView("albumView", "song", song);
            try{
                mav.addObject("album", musicService.findAlbumByID(song.getAlbumID()));
            } catch (ItemNotFoundException e) {
                return new ModelAndView("main", "message", new MessageModel("Looks like we couldn't find your album", 0));
            }

            mav.addObject("error", "addSong");
            return mav;
        }

        try{
            if(musicService.addSong(song)) {
                return new ModelAndView("albumView", "album", musicService.findAlbumByID(song.getAlbumID()));
            } else {
                return new ModelAndView("main", "message", new MessageModel("There was a problem creating your song.", 0));
            }
        } catch (ItemAlreadyExistsException e) {
            return new ModelAndView("main", "message", new MessageModel("This song already exists in our records.", 0));
        } catch (ItemNotFoundException e) {
            return new ModelAndView("main", "message", new MessageModel("It looks like there may have been an issue with the album you're trying to add a song to", 0));
        } catch (Exception e){
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    @RequestMapping(path = "/editSong", method = RequestMethod.POST)
    public ModelAndView editSong(@Valid @ModelAttribute("song") SongModel song, BindingResult result){

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("albumView", "song", song);
            try{
                mav.addObject("album", musicService.findAlbumByID(song.getAlbumID()));
            } catch (ItemNotFoundException e){
                return new ModelAndView("main", "message", new MessageModel("Looks like we couldn't find your album", 0));
            }

            mav.addObject("error", "editSong"+song.getID());
            return mav;
        }

        try{
            if(musicService.editTrackInfo(song) > 0){
                return new ModelAndView("albumView", "album", musicService.findAlbumByID(song.getAlbumID()));
            } else {
                return new ModelAndView("main", "message", new MessageModel("There was a problem editing your song.", 0));
            }
        } catch (ItemNotFoundException e) {
            return new ModelAndView("main", "message", new MessageModel("It looks like we can't find the song you're trying to edit", 0));
        } catch (Exception e){
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }

    @RequestMapping(path = "/deleteSong", method = RequestMethod.POST)
    public ModelAndView deleteSong(@ModelAttribute("song") SongModel song){
        try{
            if(musicService.removeTrack(song) > 0){
                return new ModelAndView("albumView", "album", musicService.findAlbumByID(song.getAlbumID()));
            } else {
                return new ModelAndView("main", "message", new MessageModel("There was a problem deleting your song.", 0));
            }
        } catch (ItemNotFoundException e) {
            return new ModelAndView("main", "message", new MessageModel("It looks like we can't find the song you're trying to delete", 0));
        } catch (Exception e){
            return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }
}
