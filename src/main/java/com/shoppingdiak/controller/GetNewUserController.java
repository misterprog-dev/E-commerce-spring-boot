package com.shoppingdiak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingdiak.model.Product;
import com.shoppingdiak.model.User;
import com.shoppingdiak.repository.ProductRepo;
import com.shoppingdiak.security.PasswordResetToken;
import com.shoppingdiak.security.Role;
import com.shoppingdiak.security.UserRole;
import com.shoppingdiak.service.UserSecurityService;
import com.shoppingdiak.service.UserService;
import com.shoppingdiak.util.Mappings;
import com.shoppingdiak.util.ViewNames;
import com.shoppingdiak.utility.MailConstructor;
import com.shoppingdiak.utility.SecurityUtility;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class GetNewUserController {
	
	//Controller
	
    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private ProductRepo productService;

    @RequestMapping(value = "/index")
    public String redirect() {
        return "redirect:/";
    }


    @GetMapping(Mappings.ACCOUNT_LOGIN)
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView(ViewNames.LOGIN_PAGE);
        mv.addObject("classActiveLogin", true);
        return mv;
    }

    /*
     * Home Page - GET
     */
    @GetMapping(Mappings.HOME)
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView(ViewNames.HOME_PAGE);
        List<Product> productList = productService.findAll();
        mv.addObject("productList", productList);

        mv.addObject("activeTabHome", true);
        return mv;
    }

    @PostMapping(Mappings.SETTING)
    public String register(ModelMap modelMap, Locale locale, @RequestParam("token") String token){
        PasswordResetToken passToken = userService.getPasswordTokenForUser(token);
        if (passToken == null) {
            String message = "Token Invalide";
            modelMap.addAttribute("message", message);
            return "redirect:/login";
        }
        User user = passToken.getUser();
        String username = user.getUsername();
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Boolean lockedIn = false;
        if(user != null){
            lockedIn = true;
        }
        modelMap.addAttribute("userLoggedIn", lockedIn);
        modelMap.addAttribute("user", user);

        return ViewNames.MY_ACCOUNT_PAGE;
    }



    @PostMapping(Mappings.NEW_USER_FROM_EMAIL)
    public String newUserPost(HttpServletRequest request,
    						  @ModelAttribute("firstname") String firstname,
    						  @ModelAttribute("lastname") String lastname,
                              @ModelAttribute("email") String userEmail, 
                              @ModelAttribute("username") String username,
                              @ModelAttribute("phone") String phone,
                              @ModelAttribute("password") String password,
                              ModelMap modelMap) throws Exception{
    	modelMap.addAttribute("firstname", firstname);
    	modelMap.addAttribute("lastname", lastname);
        modelMap.addAttribute("email", userEmail);
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("phone", phone);
        modelMap.addAttribute("password", password);
        modelMap.addAttribute("classActiveRegister", true);

//        if(userService.findByUsername(username) != null){
//            modelMap.addAttribute("usernameExists", true);
//            return ViewNames.LOGIN_PAGE;
//        }

        if(userService.findByUsername(userEmail) != null){
            modelMap.addAttribute("emailExists", true);
            return ViewNames.LOGIN_PAGE;
        }

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(userEmail);
        user.setPhone(phone);
        user.setPassword(password);

        //String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenUser(user, token);

        //String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        //SimpleMailMessage email = mailConstructor.constructorResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        //mailSender.send(email);

        //modelMap.addAttribute("emailSent", "true");

        return ViewNames.LOGIN_PAGE;
    }


    @PostMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request, @ModelAttribute("forgot_email") String email, ModelMap model) {

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExist", true);
            return Mappings.ACCOUNT_LOGIN;
        }

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        sendmail(request, user, password);

        model.addAttribute("forgetPasswordEmailSent", "true");
        model.addAttribute("classActiveForgot", true);

        return Mappings.ACCOUNT_LOGIN;
    }

    private void sendmail(HttpServletRequest request, User user, String password) {
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructorResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(newEmail);
    }
}
