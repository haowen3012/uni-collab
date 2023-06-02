package it.unicollab.bh.controller;


import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Profile;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.ProfileService;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {


    @Autowired
    private SessionData sessionData;

    @Autowired
    private ProfileService profileService;


}