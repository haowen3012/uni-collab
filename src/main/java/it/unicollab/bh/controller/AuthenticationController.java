package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.CredentialsService;
import it.unicollab.bh.controller.validation.CredentialsValidator;
import it.unicollab.bh.controller.validation.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.print.DocFlavor;
import java.security.Principal;

@Controller
public class AuthenticationController {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    CredentialsValidator credentialsValidator;

    @Autowired
    SessionData sessionData;

    @RequestMapping(value={"/user"}, method = RequestMethod.GET)
    public String user(Model model){
        User loggedUser = this.sessionData.getLoggedUser();
        model.addAttribute("user",loggedUser);
        return "user.html";
    }

    @RequestMapping(value={"login/oauth2/user"}, method = RequestMethod.GET)
    public String oauthUser(Model model){

        return "registrationSuccessful.html"; // da modificare  domani  14/04
    }


     @RequestMapping(value ={"/user/register"}, method = RequestMethod.GET)
    public String showRegisterFrom(Model model){
         model.addAttribute("userForm", new User());
         model.addAttribute("credentialsForm", new Credentials());

         return "login_slide.html";
     }
 

     
     
     

     @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("userForm") User user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentialsForm") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model){

         //validate user and credentials fields
         this.userValidator.validate(user, userBindingResult);
         this.credentialsValidator.validate(credentials, credentialsBindingResult);

         //if neither of them had invalid contents, store the User and the Credentials into the DB
         if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()){
             //set the user and store the credentials
             //this also stores the User, thanks to Cascade.ALL polocy
             credentials.setUser(user);
             credentialsService.saveCredentials(credentials);

             return "registrationSuccessful.html";
         }
         return "login_slide.html";
     }


}
