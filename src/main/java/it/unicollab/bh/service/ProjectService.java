package it.unicollab.bh.service;

import it.unicollab.bh.model.Project;
import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    protected ProjectRepository projectRepository;



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
    public Project shareProjectWithUser(Project project, User user){
        project.addMember(user);
        return this.projectRepository.save(project);
    }

}
