package com.shoppingdiak.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.shoppingdiak.model.User;

import java.util.Locale;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    public SimpleMailMessage constructorResetTokenEmail(String cp, Locale locale, String token, User user, String pass){
        String url = cp + "/newUser?token="+token;
        String message = "\nVotre nouveau mot de passe est: " + pass + "\n Si vous souhaitez modifier votre mot de passe, veuillez nous contacter sur le email, soumailadiakite182@gmail.com";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Enregistrement d'un nouvel utilisateur- DIAK SHOP");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public SimpleMailMessage sendPriceByEmail(String name, String price, String qty, String email, String subject, String msg){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Nom Produit - " + name + " - " + subject);
        simpleMailMessage.setText("Produit : " + name + "\nMessage: " + msg + "\nDemande de prix: " + price + "\nDemandeur: " + email + "\nQuantité: " + qty);
        simpleMailMessage.setFrom(env.getProperty("support.email"));
        return simpleMailMessage;
    }
}
