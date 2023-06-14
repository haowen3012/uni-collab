package it.unicollab.bh.controller;


import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.controller.validation.PostValidator;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.PostState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.ExamService;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class PostController {


  @Autowired
  private PostService postService;

  @Autowired
  private ExamService examService;

  @Autowired
  private PostValidator postValidator;

  @Autowired
  private SessionData sessionData;



  @RequestMapping(value ={"/createPost"}, method = RequestMethod.POST)
  public String createPost(@Valid @ModelAttribute Post p, BindingResult postBindingResult, @RequestParam(value = "selected_exam", required = false) Long idExam,
                           Model model, RedirectAttributes redirectAttributes) {


    User loggedUser = this.sessionData.getLoggedUser();

    System.out.println(idExam);
    if(idExam!=null) {
      p.setExam(this.examService.getExam(idExam));
    }
    p.setOwner(loggedUser);

    this.postValidator.validate(p, postBindingResult);

   if(!postBindingResult.hasErrors()){

     this.postService.createPost(p);

     redirectAttributes.addFlashAttribute("postCreated", true);


     return "redirect:/user";

   }

   model.addAttribute("post",p);

    return "errorPostCreation";
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

  @RequestMapping(value={"/posts"}, method= RequestMethod.GET)
  public String showPosts(Model model){

    User loggedUser = this.sessionData.getLoggedUser();

    model.addAttribute("posts", this.postService.getAllPostByOwnerOrderedByCreationTIme(loggedUser));

    return "posts.html";

  }

  @RequestMapping(value="/updatePost/{idP}", method = RequestMethod.GET)
  public  String updatePost(Model model, @PathVariable("idP") Long idPost){

    Post post =this.postService.getPost(idPost);

    if(post.getDeadline().isBefore(LocalDateTime.now())){
      return "redirect:/posts";
    }

    model.addAttribute(post);


     return "updatePostForm.html";
  }


  @RequestMapping(value="/updatePost/{idP}", method = RequestMethod.POST)
  public  String updatePost(Model model, @PathVariable("idP") Long idPost,@RequestParam("selected_exam") Long idExam,@ModelAttribute Post newPost){


    model.addAttribute( "post",this.postService.updatePost(idPost, newPost, idExam));

    return "redirect:/post.html";
  }

  @RequestMapping(value="/deletePost/{idP}", method = RequestMethod.GET)
  public String deletePost(Model model , @PathVariable("idP") Long idPost, RedirectAttributes redirectAttributes){

    this.postService.deletePost(idPost);

    redirectAttributes.addFlashAttribute("postDeleted", true);


    return "redirect:/posts";
  }
}
