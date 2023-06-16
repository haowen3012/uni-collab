package it.unicollab.bh.controller;

import it.unicollab.bh.FileUploadWrapper;
import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.controller.validation.MultipartFileValidator;
import it.unicollab.bh.model.Course;

//import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.University;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.Profile;
//import it.unicollab.bh.repository.UserRepository;
import it.unicollab.bh.service.CourseService;
//import it.unicollab.bh.service.FileUploadUtil;
import it.unicollab.bh.service.ProfileService;
import it.unicollab.bh.service.UniversityService;
import it.unicollab.bh.service.UserService;


import jakarta.validation.Valid;
import org.hibernate.internal.util.MutableLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
//import org.springframework.web.servlet.view.RedirectView;




import java.io.IOException;
import java.util.*;




@Controller
public class MainController {


    @Autowired
    private SessionData sessionData;
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MultipartFileValidator multipartFileValidator;


    public MainController(){
    }


    @RequestMapping(value={"addCourse/{user_id}"}, method = RequestMethod.POST)
    public String AddCourseToUser(Model model, @PathVariable Long user_id, @RequestParam("selected-universityId") Long uni_id){

        User user = userService.getUser(user_id);
        University university = universityService.getUniversity(uni_id);
        List<Course> courseList =  courseService.getCourseByUniversity(university);

        model.addAttribute("user", user);
        model.addAttribute("courses",courseList);

        return "completeRegistration.html";
    }

    @RequestMapping(value={"setCourse/{user_id}"}, method = RequestMethod.POST)
    public String setCourseToUser(Model model, @PathVariable Long user_id,
                                  @RequestParam("selected-courseId") Long course_id){

        User user = userService.getUser(user_id);
        Course course =  courseService.getCourse(course_id);

        /*set the course*/
        user.setCourseAttended(course);

        userService.saveUser(user);

        model.addAttribute(user);

        return "registrationCompleted.html";
    }

    /************************************************************/


    /****************CREATE AND SET THE ASSOCIATION BETWEEN USER AND PROFILE*********************/

    /*
   @PostMapping("/{userId}/profile")
    public ResponseEntity<String> associateProfileWithUser(
            @PathVariable("userId") Long userId, @RequestBody Profile profile)
    {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();

        Profile p = new Profile();

        // Set other profile properties as needed
        // Set both sides of the association
        user.setProfile(p);
        userService.saveUser(user);
      profileService.saveProfile(p);
   return ResponseEntity.ok("Profile associated with the user successfully.");
    }
*/


    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String getUserProfile(Model model) {

        User loggedUser = this.sessionData.getLoggedUser();

        model.addAttribute("profile", loggedUser.getProfile());

        model.addAttribute("owner", loggedUser);

        System.out.println(loggedUser.getOwnedPosts());

        return "profile.html";
    }


    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String getProfile(Model model,@PathVariable("id")Long idUser){

        User user = this.userService.getUser(idUser);

        model.addAttribute("profile", user.getProfile());

        model.addAttribute("owner",user);

        return "profile.html";
    }
    @RequestMapping(value={"/profile/updatePersonalInformation/{idP}"}, method = RequestMethod.POST)
    public String updatePersonalInformation(@PathVariable("idP") Long idProfile, @RequestParam("infos") String pf){

        this.profileService.updatePersonalInformation(idProfile, pf);


        return "redirect:/profile";

    }


    @RequestMapping(value={"/profile/updateImages/{idP}"}, method = RequestMethod.POST)
    public String updateProfileImages(Model model, @PathVariable("idP") Long idProfile
            , @Valid @ModelAttribute FileUploadWrapper fileUploadWrapper, BindingResult fileUploadWrapperBindingResult,
                                      @RequestParam(value = "email",required = false)String email,
                                      @RequestParam(value = "address", required = false)String address,
                                      RedirectAttributes redirectAttributes){

        this.multipartFileValidator.validate(fileUploadWrapper,fileUploadWrapperBindingResult);

        if(!fileUploadWrapperBindingResult.hasErrors() ) {



            try {
                this.profileService.updateProfileImages(idProfile, fileUploadWrapper.getImage(), fileUploadWrapper.getBackground(), email, address);


            }catch(IOException e){

                redirectAttributes.addFlashAttribute("fileUploadError","An error occured while uploading the input files");

            }
        }
        else{

            System.out.println(fileUploadWrapper.getImage().isEmpty());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.fileUploadWrapper", fileUploadWrapperBindingResult);
            redirectAttributes.addFlashAttribute("fileUploadWrapper", fileUploadWrapper);


        }

        return "redirect:/profile";


    }


    @RequestMapping(value={"/profile/updateCurriculum/{idP}"}, method = RequestMethod.POST)
    public String updateProfileCurriculum(@PathVariable("idP") Long idProfile,
                                          @RequestParam(value = "curriculum",required = false) MultipartFile curriculum,BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes){

        this.multipartFileValidator.validate(curriculum,bindingResult);

        if(!bindingResult.hasErrors()) {

            try {
                this.profileService.updateProfileCurriculum(idProfile, curriculum);

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("fileUploadError", "An error occured while uploading the input files");

            }
        }else{

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.curriculum",bindingResult);
            redirectAttributes.addFlashAttribute("post",curriculum);

        }

        return "redirect:/profile";

    }

    @RequestMapping(value = "/searchProfile", method = RequestMethod.GET)
    public String searchProfile(@RequestParam("username") String username, RedirectAttributes redirectAttributes,Model model){

        if(this.userService.getUser(username)==null){

            redirectAttributes.addFlashAttribute("profileNotFound", true);
            return "redirect:/user";
        }else{

            model.addAttribute("profile",this.userService.getUser(username).getProfile());

            return "profile.html";
        }

    }


}