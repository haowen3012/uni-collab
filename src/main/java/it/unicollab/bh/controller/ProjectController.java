package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Project.ProjectState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ProjectController {

    @Autowired
    private SessionData sessionData;

    @Autowired
    private ProjectService projectService;


    @RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
    public String showProjects(Model model){

        User loggedUser = this.sessionData.getLoggedUser();

        model.addAttribute("projects",this.projectService.getProjectsByMember(loggedUser));

        System.out.println(this.projectService.getProjectsByMember(loggedUser).size());

        return "projects.html";

    }


    @RequestMapping(value = {"/project/{id}"}, method = RequestMethod.GET)
    public String getProject(Model model,@PathVariable("id") Long id){



        model.addAttribute("project", this.projectService.getProject(id));

        return "project.html";

    }

    @RequestMapping( value={"/setProjectDeadline/{idP}"}, method = RequestMethod.POST)
    public String setProjectDeadline(@PathVariable("idP") Long idProject, @RequestParam(value = "deadline", required = false)LocalDateTime deadline){

        this.projectService.setProjectDeadline(idProject,deadline);

        return "redirect:/projects";
    }

}
