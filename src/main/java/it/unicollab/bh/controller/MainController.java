package it.unicollab.bh.controller;

import it.unicollab.bh.model.Course;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.University;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.CourseService;
import it.unicollab.bh.service.FileUploadUtil;
import it.unicollab.bh.service.UniversityService;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import java.io.IOException;


import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UniversityService universityService;


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

    /******************Image********************************/
    @PostMapping("/users/save")
    public RedirectView saveUser(User user,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(fileName);
         
        User savedUser =  userService.saveUser(user);
 
        String uploadDir = "user-photos/" + savedUser.getId();
 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
         
        return new RedirectView("/users", true);
    }
    /****************CREATE AND SET THE ASSOCIATION BETWEEN USER AND PROFILE*********************/



    /******************CREATE POSTS********************************/




}
