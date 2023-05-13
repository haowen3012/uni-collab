package it.unicollab.bh.controller;

import it.unicollab.bh.configuration.FileUploadUtil;
import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.CredentialsService;
import it.unicollab.bh.controller.validation.CredentialsValidator;
import it.unicollab.bh.controller.validation.UserValidator;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.UniversityService;
import it.unicollab.bh.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.print.DocFlavor;
import java.io.IOException;
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


    @Autowired
    PostService postService;



    @RequestMapping(value ={"/"}, method = RequestMethod.GET)
    public String index() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            return "index.html";
        }
        else{

             return "redirect: /user";
        }
    }


    @RequestMapping(value={"/user"}, method = RequestMethod.GET)
    public String user(Model model){


           User loggedUser = this.sessionData.getLoggedUser();

           model.addAttribute("posts", postService.getAllByOwnerNotAndAppliedUsersNotContaining(loggedUser,loggedUser));

           return "createPost.html";
    }



/***************************************************************************************/

    @RequestMapping(value ={"/successful"}, method = RequestMethod.GET)
    public String successful(Model model){
        User loggedUser = this.sessionData.getLoggedUser();
        model.addAttribute("user",loggedUser);

        if(loggedUser.getCourseAttended()!=null ){
            return "homepage.html";
        }


        model.addAttribute("universities",universityService.getAllUniversities());
        return "registrationSuccessful.html";
    }


    /* questo metodo probabilmente dovrà essere cancellato*/
    @RequestMapping(value={"login/oauth2/user"}, method = RequestMethod.GET)
    public String oAuth2Successful(Model model){


        User loggedUser = this.sessionData.getLoggedUser();
        model.addAttribute("user",loggedUser);

        if(loggedUser.getCourseAttended()!=null ){
            model.addAttribute("posts", postService.getAllPostByOwnerNot(loggedUser));
            return "createPost.html";
        }

        model.addAttribute("universities",universityService.getAllUniversities());
        return "registrationSuccessful.html";
    }

/**************************************************************************************************/

    @RequestMapping(value ={"/login"}, method = RequestMethod.GET)
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

             model.addAttribute("user",user);
             model.addAttribute("universities",universityService.getAllUniversities());

             return "login_slide.html";
         }
         return "login_slide.html";
     }
    @PostMapping("/login/oauth2/user")
    public RedirectView saveUser(User user, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);

        User savedUser =  userService.saveUser(user);

        String uploadDir = "user-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/users", true);
    }


}
