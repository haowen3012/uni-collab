package it.unicollab.bh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    public MainController(){
    }

   @RequestMapping(value ={"/index"}, method = RequestMethod.GET)
    public String index() {
        return "index.html";
    }


}
