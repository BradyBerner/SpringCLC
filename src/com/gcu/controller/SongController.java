package com.gcu.controller;

import com.gcu.business.MusicBusinessInterface;
import com.gcu.model.AlbumModel;
import com.gcu.model.MessageModel;
import com.gcu.model.SongModel;
import com.gcu.utility.ItemAlreadyExistsException;
import com.gcu.utility.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@RequestMapping("/songs")
public class SongController {

    private MusicBusinessInterface<AlbumModel, SongModel> musicService;

    @Autowired
    private void setMusicService(MusicBusinessInterface<AlbumModel, SongModel> musicService){
        this.musicService = musicService;
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
}
