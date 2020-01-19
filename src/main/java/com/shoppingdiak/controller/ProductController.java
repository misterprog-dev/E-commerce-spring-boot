package com.shoppingdiak.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingdiak.model.Product;
import com.shoppingdiak.model.User;
import com.shoppingdiak.model.cartItem;
import com.shoppingdiak.repository.CartItemRepository;
import com.shoppingdiak.repository.ProductRepo;
import com.shoppingdiak.service.UserService;
import com.shoppingdiak.util.Mappings;
import com.shoppingdiak.util.ViewNames;
import com.shoppingdiak.utility.MailConstructor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

@Controller
public class ProductController {
	
	
    @Autowired
    private ProductRepo productService;
    
    @Autowired
	CartItemRepository cartitemRepo;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;
    
    /*
     * All Products - GET
     */
    @GetMapping(Mappings.PRODUCTS)
    public ModelAndView products(){
        ModelAndView mv = new ModelAndView(ViewNames.PRODUCTS);

        List<Product> productList = productService.findAll();
        
        mv.addObject("activeTabProducts", true);
        mv.addObject("productList", productList);
        
        return mv;
    }
    
    /*
     * Nos divans - GET
     */
    @GetMapping(Mappings.DIVANS)
    public ModelAndView divans(){
        ModelAndView mv = new ModelAndView(ViewNames.DIVANS);

        List<Product> productList = productService.findProductByCategoryContaining("divan");
        //List<Product> productList = productService.findAll();
        
        mv.addObject("activeTabDivans", true);
        mv.addObject("productList", productList);
        //mv.addObject("produitLitsList", produitLitsList);
        
        return mv;
    }
    
    /*
     * Nos lits - GET
     */
    @GetMapping(Mappings.LITS)
    public ModelAndView lits (){
        ModelAndView mv = new ModelAndView(ViewNames.LITS);

        List<Product> productList = productService.findProductByCategoryContaining("lit");
        //List<Product> productList = productService.findAll();
        
        mv.addObject("activeTabLits", true);
        mv.addObject("productList", productList);
        //mv.addObject("produitLitsList", produitLitsList);
        
        return mv;
    }
    
    /*
     * Nos salons - GET
     */
    @GetMapping(Mappings.SALONS)
    public ModelAndView salons (){
        ModelAndView mv = new ModelAndView(ViewNames.SALONS);

        List<Product> productList = productService.findProductByCategoryContaining("salon");
        //List<Product> productList = productService.findAll();
        
        mv.addObject("activeTabSalon", true);
        mv.addObject("productList", productList);
        //mv.addObject("produitLitsList", produitLitsList);
        
        return mv;
    }
    
    /*
     * Nos tables à manger - GET
     */
    @GetMapping(Mappings.TABLES)
    public ModelAndView tables(){
        ModelAndView mv = new ModelAndView(ViewNames.TABLES);

        List<Product> productList = productService.findProductByCategoryContaining("table");
        //List<Product> productList = productService.findAll();
        
        mv.addObject("activeTabTable", true);
        mv.addObject("productList", productList);
        //mv.addObject("produitLitsList", produitLitsList);
        
        return mv;
    }
    
    
    @PostMapping("/getPriceByEmail")
    public String sendPriceThroughEmail(
            @ModelAttribute("name") String name, @ModelAttribute("email") String email,
            @ModelAttribute("subject") String subject, @ModelAttribute("msg") String msg,
            @ModelAttribute("price") String price, @ModelAttribute("qty") String qty, Model model
    ){
        SimpleMailMessage simpleMailMessage = mailConstructor.sendPriceByEmail(name, price, qty, email, subject, msg);

        mailSender.send(simpleMailMessage);
        model.addAttribute("emailsend", true);
        return "products";
    }
    
}
