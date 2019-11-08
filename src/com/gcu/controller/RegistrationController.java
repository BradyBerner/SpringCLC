package com.gcu.controller;

import com.gcu.business.UserBusinessInterface;
import com.gcu.model.MessageModel;
import com.gcu.model.UserModel;
import com.gcu.utility.ItemAlreadyExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

/*
Controller for handling the user registration process. Currently just hard-coded to send the user to the login page if
all entered information is valid, will eventually be attached to the database to store new user accounts.
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

	
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
    Method for displaying the registration form page
     */
    @RequestMapping(path = "/portal", method = RequestMethod.GET)
    public ModelAndView displayPage(){
        return new ModelAndView("registrationPortal", "user", new UserModel(0, "", "", "", "", "", "", 0, 0));
    }

    /*
    Method that will eventually handle validating and sending new user information to be stored in the database. For the
    time being simply checks that all of the user's input was valid and routes them to the login page or back to the
    registration form if their input is invalid.
    merge the login and registration controllers, or use a third option I can't think of in order to resolve this while
    still going directly to the login page. Meanwhile, it's set up to route to a landing page which has a link to login.
     */
    @RequestMapping(path = "/doRegistration", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("user") UserModel user, BindingResult result){

    	//Returns to registration page in the event of validation errors
        if(result.hasErrors()){
            return new ModelAndView("registrationPortal", "user", user);
        }
        
        //Attempts to register a user in the database
        try 
        {
        	//Attempts registration
        	userService.register(user);
        	
        	//Returns to the main page upon success
        	return new ModelAndView("main", "message", new MessageModel("Registration Successful!", 1));
        }
        //Handles the event that a user with this email already exists
        catch(ItemAlreadyExistsException e)
        {
        	return new ModelAndView("registrationPortal", "message", new MessageModel("A user already exists with this email. Please log in or register with a different email", 0));
        }
        //Catches back-end exceptions
        catch(Exception e)
        {
        	return new ModelAndView("registrationPortal", "message", new MessageModel("An unknown error has occured", 0));
        }
    }
}
