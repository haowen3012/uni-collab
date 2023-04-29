package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.CredentialsService;
import it.unicollab.bh.controller.validation.CredentialsValidator;
import it.unicollab.bh.controller.validation.UserValidator;
import it.unicollab.bh.service.UniversityService;
import it.unicollab.bh.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    UserService userService;

    @Autowired
    UniversityService universityService;

    @RequestMapping(value={"/user"}, method = RequestMethod.GET)
    public String user(Model model){
        User loggedUser = this.sessionData.getLoggedUser();
        model.addAttribute("user",loggedUser);
        return "homepage.html";
    }



    @RequestMapping(value={"login/oauth2/user"}, method = RequestMethod.GET)
    public String addUniversityToUser(Model model){

        User loggedUser = this.sessionData.getLoggedOAuth2User();
        model.addAttribute("user",loggedUser);
        model.addAttribute("universities",universityService.getAllUniversities());
        return "registrationSuccessful.html";
    }


    @RequestMapping(value ={"/user/register"}, method = RequestMethod.GET)
    public String showLoginAndRegisterForm(Model model ){

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

             User loggedUser = this.sessionData.getLoggedUser();
             model.addAttribute("user",loggedUser);

             return "registrationSuccessful.html";
         }
         return "login_slide.html";
     }


}
