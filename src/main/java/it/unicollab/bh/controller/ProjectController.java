package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Project.ProjectState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        return "project.html";

    }
}
