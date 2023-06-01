package it.unicollab.bh.controller;

import it.unicollab.bh.configuration.FileUploadUtil;
import it.unicollab.bh.controller.session.SessionData;
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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

<<<<<<< HEAD
    /************************************************************/
=======


    /****************CREATE AND SET THE ASSOCIATION BETWEEN USER AND PROFILE*********************/
   @PostMapping("/{userId}/profile")
    public ResponseEntity<String> associateProfileWithUser(
            @PathVariable("userId") Long userId, @RequestBody Profile profile)
    {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
>>>>>>> fcf9c6c9eb00c3750f393ed3b47701cf9b204ccd

        User user = userOptional.get();

        Profile p = new Profile();

        // Set other profile properties as needed
        // Set both sides of the association
        user.setProfile(p);
        userService.saveUser(user);
      profileService.saveProfile(p);
   return ResponseEntity.ok("Profile associated with the user successfully.");
    }

    @RequestMapping(value={"/profile"}, method = RequestMethod.GET)
    public String getUserProfile(Model model){

        User loggedUser = this.sessionData.getLoggedUser();

        model.addAttribute("profile",loggedUser.getProfile());

        model.addAttribute("owner",loggedUser );

        return "profile.html";
    }



}
