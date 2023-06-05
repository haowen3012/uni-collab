package it.unicollab.bh.service;

import it.unicollab.bh.model.Project.Project;
import it.unicollab.bh.model.Project.ProjectState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserService userService;



    @Transactional
    public Project getProject(long id){
        Optional<Project> result = this.projectRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Project saveProject(Project project){
        return this.projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Project project){
        this.projectRepository.delete(project);
    }

    @Transactional
    public void shareProjectWithUser(Project project, User user){

        project.addMember(user);
        user.addProject(project);
        this.userService.saveUser(user);
        this.projectRepository.save(project);
    }

    @Transactional
    public Collection<Project> getProjectsByMember(User member){

        return this.projectRepository.findByMembersContains(member);

    }

    @Transactional
    public Collection<Project> getProjectsByMemeberOrderByCreationTime(User member){
        return this.projectRepository.findByMembersContainsOrderByCreationTimestampDesc(member);
    }


    @Transactional
    public void setProjectDeadline(Long idProject, LocalDateTime deadline){

       Project project = this.getProject(idProject);

       project.setDeadline(deadline);

       this.saveProject(project);
    }


    @Transactional
    public  Collection<Project> getTerminateProjects(LocalDateTime localDateTime, ProjectState projectState){

        return this.projectRepository.findByDeadlineBeforeAndProjectStateNot(localDateTime, projectState);
    }
}


