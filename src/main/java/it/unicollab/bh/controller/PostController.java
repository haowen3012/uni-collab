package it.unicollab.bh.controller;

import ch.qos.logback.core.model.Model;
import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {


  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @Autowired
  private SessionData sessionData;

    @RequestMapping(value ={"/createPost"}, method = RequestMethod.POST)
    public String creatPost(@ModelAttribute Post post, Model model ) {



      postService.savePost(post);

      User loggedUser = this.sessionData.getLoggedOAuth2User();


      return "createPost.html";
    }


}
