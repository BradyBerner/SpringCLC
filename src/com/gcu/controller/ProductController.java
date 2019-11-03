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

    @RequestMapping(path = "/doCreate", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("product") ProductModel product, BindingResult result, HttpSession session){

        if(result.hasErrors()){
            return new ModelAndView("productCreationPortal", "product", product);
        }

        Principal principal = (Principal)session.getAttribute("principal");
        product.setUserID(principal.getID());

        try
        {
        	if(productService.add(product))
        	{
        		String m = String.format("New Product Successfully Created");
            	return new ModelAndView("main", "message", new MessageModel(m, 1));
        	}
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
