package it.unicollab.bh.controller;

import it.unicollab.bh.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {


  @Autowired
  private PostService postService;

    @RequestMapping(value ={"/createPost"}, method = RequestMethod.POST)
    public String creatPost() {



      return "hompage.html";
    }


}
