package it.unicollab.bh.controller;

import ch.qos.logback.core.model.Model;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    public MainController(){
    }

   @RequestMapping(value ={"/index"}, method = RequestMethod.GET)
    public String index() {
        return "index.html";
    }


    @RequestMapping(value ={"/addUniAndCourse/{id}"}, method =RequestMethod.POST)
    public String addUniAndCourse(@PathVariable Long id, Model model){ // add user's university and course of study

        return "";
    }

}
