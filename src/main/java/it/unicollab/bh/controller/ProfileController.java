package it.unicollab.bh.controller;


import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.ProfileService;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController {



    @Autowired
    private SessionData sessionData;



    @RequestMapping(value={"/profile"}, method = RequestMethod.GET)
    public String getUserProfile(Model model){

        User loggedUser = this.sessionData.getLoggedUser();

        model.addAttribute("profile",loggedUser.getProfile());

        model.addAttribute("owner",loggedUser );

        return "profile.html";
    }
}
