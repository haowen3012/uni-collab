package it.unicollab.bh.controller;


import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.PostState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {


  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @Autowired
  private SessionData sessionData;

  @RequestMapping(value ={"/createPost"}, method = RequestMethod.POST)
  public String createPost(@ModelAttribute Post p, Model model ) {


    User loggedUser = this.sessionData.getLoggedUser();

    Post post = new Post(p.getProjectName(),p.getProjectDescription(),p.getMembership(),loggedUser,p.getDeadline());

    postService.savePost(post);



    return "redirect:/user";
  }


  /*@RequestMapping(value={"/filterAndOrder"}, method = RequestMethod.GET)
  public String filterAndOrder(@RequestParam(name="filter") String filter,@RequestParam(name="orderBy",defaultValue = "false") boolean orderBy , Model model){

    User loggedUser = this.sessionData.getLoggedUser();
    if(orderBy){
      model.addAttribute("posts",this.postService.getHomePagePostOrderedByCreationTimeDesc(loggedUser,loggedUser, PostState.ACTIVE));
    }

    return "createPost.html";
  }
*/
}
