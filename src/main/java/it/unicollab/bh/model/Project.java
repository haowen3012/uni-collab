package it.unicollab.bh.model;

import jakarta.persistence.*;


import javax.sound.sampled.Port;
import java.util.Set;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private ProjectState projectState;

    @ManyToMany(mappedBy="projects")
    private Set<User> members;


    public Project(){}

    public Project(String name, String description, ProjectState projectState, Set<User> members) {
        this.name = name;
        this.description = description;
        this.projectState = projectState;
        this.members = members;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addMember(User user) {
        this.getMembers().add(user);
    }
}
