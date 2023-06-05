package it.unicollab.bh.controller;

import it.unicollab.bh.FileUploadWrapper;
import it.unicollab.bh.configuration.FileUploadUtil;
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

        return "profile.html";
    }

    @RequestMapping(value={"/profile/updatePersonalInformation/{idP}"}, method = RequestMethod.POST)
    public String updatePersonalInformation(Model model,@PathVariable("idP") Long idProfile, @RequestParam("infos") String pf){

        model.addAttribute(this.profileService.updatePersonalInformation(idProfile, pf));


        return "redirect:/profile";

    }


    @RequestMapping(value={"/profile/updateImages/{idP}"}, method = RequestMethod.POST)
    public String updateProfieImages(Model model, @PathVariable("idP") Long idProfile
            , @Valid @ModelAttribute FileUploadWrapper fileUploadWrapper, BindingResult fileUploadWrapperBindingResult,
                                     @RequestParam(value = "email",required = false)String email,
                                     @RequestParam(value = "address", required = false)String address,
                                     RedirectAttributes redirectAttributes){

          this.multipartFileValidator.validate(fileUploadWrapper,fileUploadWrapperBindingResult);

          if(!fileUploadWrapperBindingResult.hasErrors() ) {


              this.profileService.updateProfileImages(idProfile,fileUploadWrapper.getImage(), fileUploadWrapper.getBackground(), email, address);

              return "redirect:/profile";
          }
          else{

              System.out.println(fileUploadWrapper.getImage().isEmpty());
              redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.fileUploadWrapper", fileUploadWrapperBindingResult);
              redirectAttributes.addFlashAttribute("fileUploadWrapper", fileUploadWrapper);

              return "redirect:/profile";

          }



    }


    @RequestMapping(value={"/profile/updateCurriculum/{idP}"}, method = RequestMethod.POST)
    public String updateProfileCurriculum(Model model, @PathVariable("idP") Long idProfile,
                                          @RequestParam(value = "curriculum",required = false) MultipartFile curriculum){

        this.profileService.updateProfileCurriculum(idProfile,curriculum);

        return "redirect:/profile";
    }



}
