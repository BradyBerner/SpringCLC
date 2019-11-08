package com.gcu.controller;

import com.gcu.business.MusicBusinessInterface;
import com.gcu.model.*;
import com.gcu.utility.ItemNotFoundException;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/library")
public class AlbumController {

    private MusicBusinessInterface<AlbumModel, SongModel> musicService;

    @Autowired
    private void setMusicService(MusicBusinessInterface<AlbumModel, SongModel> musicService){
        this.musicService = musicService;
    }

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

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ModelAndView addAlbum(@Valid @ModelAttribute("album") AlbumModel album, BindingResult result, HttpSession session){

        if(result.hasErrors()){
           ModelAndView mav = new ModelAndView("library","album", album);
           try {
               mav.addObject("library", musicService.findAllAlbumsByUser(((Principal)session.getAttribute("principal")).getID()));
           } catch (ItemNotFoundException e) {
               mav.addObject("message", new MessageModel("You don't currently have any items in your library", 0));
           }

           mav.addObject("error", "");
           return mav;
        }

        return displayLibrary(session);
    }
}
