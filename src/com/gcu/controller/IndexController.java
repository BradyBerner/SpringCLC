package com.gcu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This class exists to handle the initial mapping of the application
 */
@Controller
@RequestMapping("/")
public class IndexController 
{

	/**
	 * This method redirects users to the main page upon accessing the root of the application
	 * @return String A text representation of the main page interpreted by Spring as a view
	 */
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String displayPage(){
        return "main";
    }
}
