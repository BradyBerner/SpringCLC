package com.gcu.controller;

import com.gcu.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

/*
Controller for user login. Currently using hard-coded validation logic until a database is implemented into the application
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /*
    Method for displaying login form page
     */
    @RequestMapping(path = "/portal", method = RequestMethod.GET)
    public ModelAndView displayPage(){
        //Change these view names as you see fit I just tried to make it obvious what they led to
        return new ModelAndView("loginPortal", "user", new UserModel());
    }

    /*
    Method to validate user input as well as checking their input information against hard-coded information which will
    eventually be changed to being checked against the database
     */
    @RequestMapping(path = "doLogin", method = RequestMethod.POST)
    public ModelAndView authenticate(@Valid @ModelAttribute("user") UserModel user, BindingResult result){

        if(result.hasErrors()){
            return new ModelAndView("loginPortal", "user", user);
        }

        if(user.getUsername().equals("tester") && user.getPassword().equals("testing")){
            return new ModelAndView("loginSuccess", "user", user);
        } 
        //(temporarily?) returns to the login page with a message stating failure until a better solution is presented
        else {
        	 ModelAndView response = new ModelAndView();
        	 response.setViewName("loginPortal");
        	 response.addObject("user", user);
        	 response.addObject("failed", "Incorrect Username or Password");
           	return response;
        }
    }
}
