package com.gcu.controller;

import com.gcu.model.MessageModel;
import com.gcu.model.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public ModelAndView displayCreate(){
        return new ModelAndView("productCreationPortal", "product", new ProductModel());
    }

    @RequestMapping(path = "/doCreate", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("product") ProductModel product, BindingResult result){

        if(result.hasErrors()){
            return new ModelAndView("productCreationPortal", "product", product);
        }

        if(true) {
            return new ModelAndView("main", "message", new MessageModel("New Product Successfully Created", 1));
        } else {
            return new ModelAndView("main", "message", new MessageModel("There was a problem creating your product", 0));
        }
    }
}
