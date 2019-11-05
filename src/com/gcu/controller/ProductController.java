package com.gcu.controller;

import com.gcu.business.ProductBusinessInterface;
import com.gcu.model.MessageModel;
import com.gcu.model.Principal;
import com.gcu.model.ProductModel;
import com.gcu.utility.ItemAlreadyExistsException;

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
 * 
 */
@Controller
@RequestMapping("/product")
public class ProductController {

	//Class scoped business service to handle back-end logic
    private ProductBusinessInterface<ProductModel> productService;
		
		/**
		 * This function injects a user business service at runtime for use in this particular class
		 * @param productService A business service which handles any functions related to login
		 */
	@Autowired
	public void setProductBusinessService(ProductBusinessInterface<ProductModel> productService)
	{
			this.productService=productService;
	}
		
    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public ModelAndView displayCreate(){
        return new ModelAndView("productCreationPortal", "product", new ProductModel());
    }

    @RequestMapping(path = "/library", method = RequestMethod.GET)
    public ModelAndView displayLibrary(HttpSession session){
	    try{
            return new ModelAndView("library", "library", productService.findAllWithID(((Principal)session.getAttribute("principal")).getID()));
        } catch (Exception e){
            return new ModelAndView("main", "message", new MessageModel("There was an error retrieving your library", 0));
        }
    }

    @RequestMapping(path = "/doCreate", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("product") ProductModel product, BindingResult result, HttpSession session){
    	
    	//returns to product creation form in the event of validation errors
        if(result.hasErrors()){
            return new ModelAndView("productCreationPortal", "product", product);
        }
        
        //Sets the user id of the product using the logged in user ID stored in the session
        product.setUserID(((Principal)session.getAttribute("principal")).getID());
        
        //Attempts to persist the product
        try
        {
        	//Attempts to add persist a product
        	if(productService.addProduct(product))
        	{
        		///Returns success message
        		String m = "New Product Successfully Created";
            	return new ModelAndView("main", "message", new MessageModel(m, 1));
        	}
        	//Returns this message if by some miracle the back end bypasses all exceptions only to return a false value in product creation
        	else
            	return new ModelAndView("main", "message", new MessageModel("There was a problem creating your product.", 0));
        }
        
        catch(ItemAlreadyExistsException e)
        {
        	return new ModelAndView("main", "message", new MessageModel("This product already exists in our records.", 0));
        }
        catch(Exception e)
        {
        	return new ModelAndView("main", "message", new MessageModel("An unknown error has occurred", 0));
        }
    }
}
