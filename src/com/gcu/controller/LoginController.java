package com.gcu.controller;

import com.gcu.business.UserBusinessInterface;
import com.gcu.model.CredentialsModel;
import com.gcu.model.Principal;
import com.gcu.model.UserModel;
import com.gcu.utility.ItemNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/*
Controller for user login. Currently using hard-coded validation logic until a database is implemented into the application
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	//Class scoped business service to handle back-end logic
	private UserBusinessInterface<UserModel> userService;
	
	/**
	 * This function injects a user business service at runtime for use in this particular class
	 * @param userService A business service which handles any functions related to login
	 */
	@Autowired
	public void setUserBusinessService(UserBusinessInterface<UserModel> userService)
	{
		this.userService=userService;
	}
	
    /*
    Method for displaying login form page
     */
    @RequestMapping(path = "/portal", method = RequestMethod.GET)
    public ModelAndView displayPage(){
        //Change these view names as you see fit I just tried to make it obvious what they led to
        return new ModelAndView("loginPortal", "credentials", new CredentialsModel());
    }

    /*
    Method to validate user input as well as checking their input information against hard-coded information which will
    eventually be changed to being checked against the database
     */
    @RequestMapping(path = "doLogin", method = RequestMethod.POST)
    public ModelAndView authenticate(@Valid @ModelAttribute("credentials") CredentialsModel credentials, BindingResult result, HttpSession session){

        if(result.hasErrors()){
            return new ModelAndView("loginPortal", "credentials", credentials);
        }
        
        //Attempts to authenticate and log on this user
        try
        {
        	//Setting the credentials to a user model in order to satisfy the program's interfaces without typecasts
        	UserModel user = new UserModel();
        	user.setCredentials(credentials);
        	
        	//Attempts to authenticate user
        	UserModel foundUser = userService.authenticate(user);

        	session.setAttribute("principal", new Principal(foundUser.getCredentials().getUsername(), foundUser.getID(), foundUser.getRole(), foundUser.getStatus()));
        	
        	//Redirecting user to main page upon success (Currently returns login credentials. Later will place a user in the session)
            return new ModelAndView("main", "credentials", credentials);
        } 
        //Handles the event that a user is not found in the database
        catch(ItemNotFoundException e)
        {
        	 ModelAndView response = new ModelAndView();
        	 response.setViewName("loginPortal");
        	 response.addObject("credentials", credentials);
        	 response.addObject("failed", "Incorrect Username or Password");       	 
           	 return response;
        }
        //Handling unknown errors
        catch(Exception e)
        {
       	 	ModelAndView response = new ModelAndView();
       		response.setViewName("loginPortal");
       		response.addObject("credentials", credentials);
       		response.addObject("failed", "An unknown error has occurred");       	 
          	 return response;
        }
    }

    @RequestMapping(path = "signOut", method = RequestMethod.GET)
    public String logout(HttpSession session){
    	session.removeAttribute("principal");

    	return "main";
	}
}
