package it.unicollab.bh.controller;

import it.unicollab.bh.model.Course;
import it.unicollab.bh.model.University;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.CourseService;
import it.unicollab.bh.service.UniversityService;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

   @RequestMapping(value ={"/index"}, method = RequestMethod.GET)
    public String index() {
        return "index.html";
    }


    @RequestMapping(value={"AddCourse/{user_id}/{uni_id}"}, method = RequestMethod.GET)
    public String AddCourseToUser(Model model, @PathVariable Long user_id, @PathVariable Long uni_id){

        User user = userService.getUser(user_id);
        University university = universityService.getUniversity(uni_id);
        List<Course> courseList =  courseService.getCourseByUniversity(university);

        model.addAttribute(user);
        model.addAttribute(courseList);

        return "completeRegistration2.html";
    }

    @RequestMapping(value={"setCourse/{user_id}/{course_id"}, method = RequestMethod.GET)
    public String setCourseToUser(Model model, @PathVariable Long user_id,
                                  @PathVariable Long course_id){

        User user = userService.getUser(user_id);
        Course course =  courseService.getCourse(course_id);

        /*set the course*/
        user.setCourseAttended(course);

        userService.saveUser(user);

        model.addAttribute(user);

        return "successful.html";
    }
}
