package it.unicollab.bh.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;



@Entity
public class Post {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String projectName;

    private String projectDescription;

    private Integer membership;

    @ManyToOne
    private User owner;

    @ManyToMany
    private Collection<User> acceptedUsers;


    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime lastUpdateTimestamp;


    /**
     *  the post deadline. After this date, the post is no longer visible to users who are not the owner
     */
    private LocalDateTime  deadline;


    public Post(String projectName, String projectDescription, Integer membership, User owner,LocalDateTime deadline) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.membership = membership;
        this.owner = owner;
        this.deadline = deadline;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Integer getMembership() {
        return membership;
    }

    public void setMembership(Integer membership) {
        this.membership = membership;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<User> getAcceptedUsers() {
        return acceptedUsers;
    }

    public void setAcceptedUsers(Collection<User> acceptedUsers) {
        this.acceptedUsers = acceptedUsers;
    }


    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
