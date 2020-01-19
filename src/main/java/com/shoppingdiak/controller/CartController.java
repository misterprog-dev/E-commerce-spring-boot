/**
 * 
 */
package com.shoppingdiak.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.shoppingdiak.model.Cart;
import com.shoppingdiak.model.Product;
import com.shoppingdiak.model.User;
import com.shoppingdiak.model.cartItem;
import com.shoppingdiak.repository.CartItemRepository;
import com.shoppingdiak.repository.CartRepository;
import com.shoppingdiak.repository.ProductRepo;
import com.shoppingdiak.security.Role;
import com.shoppingdiak.security.UserRole;
import com.shoppingdiak.service.UserService;
import com.shoppingdiak.util.Mappings;
import com.shoppingdiak.util.URLUtils;
import com.shoppingdiak.util.ViewNames;
import com.shoppingdiak.utility.SecurityUtility;
 

//**************************************
/**
 * @author HP
 *
 */
@Controller
public class CartController {

	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	CartItemRepository cartitemRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	private UserService userService;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	

	
	@RequestMapping("/buy")
    public String viewProduct(@RequestParam List<Long> id, Model modelMap, Principal principal){
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            modelMap.addAttribute("user", user);
        }

        Product p =productRepo.findProductById(id.get(1));
        if ( p.isActive()) {
        	modelMap.addAttribute("product", p);
        	modelMap.addAttribute("page", id.get(0));
        }
        
        return "single";
    }
	
	@GetMapping(path ="/addItem")
    public String addProductCart(@RequestParam List<Long> id, Model modelMap, Principal principal){ //@PathParam("page") int page, 
      
		Long idproduitLong = id.get(1);
		int page = id.get(0).intValue();
		
		if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            modelMap.addAttribute("user", user);
        }
		
        Product p =productRepo.findProductById(idproduitLong);

        if ( p.isActive()) {
        
        	cartItem c = new cartItem();
        	c.setProductnum(idproduitLong);
        	c.setProductname(p.getTitle());
        	c.setProductimage(p.getProduct_image());
        	c.setProductprice(p.getPrice());
        	c.setQuantite(1);
        	cartitemRepo.save(c);
 
        }
        
        String viewString = ViewNames.PRODUCTS;
        List<Product> productList = productRepo.findAll();
        
        switch (page) {
			case 1:
				viewString = ViewNames.PRODUCTS;	
				break;
			case 2:
				productList = productRepo.findProductByCategoryContaining("lit");	
				viewString = ViewNames.LITS;
				break;
			case 3:
				viewString = ViewNames.DIVANS;
				productList = productRepo.findProductByCategoryContaining("divan");	
				break;
			case 4:
				viewString = ViewNames.SALONS;
				productList = productRepo.findProductByCategoryContaining("salon");
				
				break;
			case 5:
				viewString = ViewNames.TABLES;
				productList = productRepo.findProductByCategoryContaining("table");
				
				break;
	
			default:
				break;
		}
        
        modelMap.addAttribute("productList", productList);
        
        return viewString;
    }
	
	@RequestMapping("/clearitem")
    public String clearCart(Model modelMap, Principal principal){ //@PathParam("page") int page, @PathParam("id") int page, 
      
		if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            modelMap.addAttribute("user", user);
        }
		
        //Clear items table
		cartitemRepo.deleteAll();
        
        String viewString = ViewNames.PRODUCTS;
        List<Product> productList = productRepo.findAll();
        
        modelMap.addAttribute("productList", productList);
        
        return viewString;
    }
	
	@RequestMapping("/deleteitem")
    public String deleteItem(@PathParam("id") Long id, Model modelMap, Principal principal){ //@PathParam("page") int page, @PathParam("id") int page, 
		
        cartitemRepo.deleteById(id);
		
		List<cartItem> productList = cartitemRepo.findAll() ;
        
        if(!productList.isEmpty()) {
        	double total=0.0;
            
            for (cartItem cartItem : productList) {
    			total += cartItem.getProductprice();
    		}
            modelMap.addAttribute("NotemptyList", true);
            modelMap.addAttribute("productList", productList);
            modelMap.addAttribute("total", total);
        }else {
        	modelMap.addAttribute("emptyList", true);
		}

        return "cart";
        
    }
	
	
	
	@RequestMapping("/cart")
    public String viewCart(Model modelMap, Principal principal){ //@PathParam("id") Long id, 
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            modelMap.addAttribute("user", user);
        }
        
        List<cartItem> productList = cartitemRepo.findAll() ;
        
        ArrayList<Long> idList = new ArrayList<Long>();
        for (cartItem c : productList) {
			idList.add(c.getProductnum());
		}
        
        if(!productList.isEmpty()) {
        	double total=0.0;
            
            for (cartItem cartItem : productList) {
    			total += cartItem.getProductprice();
    		}
            modelMap.addAttribute("NotemptyList", true);
            modelMap.addAttribute("productList", productList);
            modelMap.addAttribute("total", total);
        }else {
        	modelMap.addAttribute("emptyList", true);
		}
        
        modelMap.addAttribute("idList", idList);

        return "cart";

    }
	
	@GetMapping("/saveCustomer")
	public String saveCustomer() {
		return "saveCustomer";
	}
	
	@PostMapping("/saveCustomerInfo")
    public String saveCustomerInfo(HttpServletRequest request,
    						  @ModelAttribute("firstname") String firstname,
    						  @ModelAttribute("lastname") String lastname,
                              @ModelAttribute("email") String email, 
                              @ModelAttribute("contact") String contact,
                              @ModelAttribute("adresse") String adresse,
                              ModelMap modelMap) throws Exception{
    	
		ArrayList<String> infoUserList = new ArrayList<String>();
		infoUserList.add(firstname);
		infoUserList.add(lastname);
		infoUserList.add(email);
		infoUserList.add(contact);
		infoUserList.add(adresse);
		
		modelMap.addAttribute("infoUserList", infoUserList);
		modelMap.addAttribute("dataSaved", true);
		modelMap.addAttribute("Saved", false);
        return "cart";
    }
	
	@RequestMapping("/success")
    public String viewSuccess(@RequestParam List<String> details, @RequestParam List<Long> idList, Model modelMap){
     
		//Clear items table
		cartitemRepo.deleteAll();
		
		
		for (Long id : idList) {
			Cart cart = new Cart();
			cart.setNom(details.get(0));
			cart.setPrenom(details.get(1));
			cart.setEmail(details.get(2));
			cart.setAdresse(details.get(3) + " " + details.get(4) + " " + details.get(5));
			cart.setIdproduit(id);
			cart.setQuantite(1);
			Product p =productRepo.findProductById(id);
			cart.setPrixUnitaire(p.getPrice());
			
			cartRepo.save(cart);
			
			//We update product
			if(p.getInStockNumber() > 0) {
				p.setInStockNumber(p.getInStockNumber() - 1);
			}
			if(p.getInStockNumber() == 0) {
				p.setActive(false);
			}
			
			productRepo.save(p);
			
		}
		
		modelMap.addAttribute("nom", details.get(0));
		modelMap.addAttribute("prenom", details.get(1));
		modelMap.addAttribute("email", details.get(2));
		modelMap.addAttribute("adresse", details.get(3) + " " + details.get(4));   
		modelMap.addAttribute("pays", details.get(5));
		modelMap.addAttribute("total", details.get(6));
		modelMap.addAttribute("monnaie", details.get(7));
		
        return "success";
    }
	
	
}
