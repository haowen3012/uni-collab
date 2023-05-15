package it.unicollab.bh.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> acceptedUsers;


    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime lastUpdateTimestamp;


    @ManyToMany(mappedBy = "appliedPosts")
    private Set<User> appliedUsers;


    /**
     *  the post deadline. After this date, the post is no longer visible to users who are not the owner
     */
    private LocalDateTime  deadline;

    @Enumerated(EnumType.STRING)
    private PostState postState;


    @ManyToOne
    private Exam exam;

    public Post(){

    }

    public Post(String projectName, String projectDescription, Integer membership, User owner,LocalDateTime deadline,Exam exam) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.membership = membership;
        this.owner = owner;
        this.acceptedUsers = new HashSet<>();
        this.deadline = deadline;
        this.postState = PostState.ACTIVE;
        this.exam = exam;
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

    public Set<User> getAcceptedUsers() {
        return acceptedUsers;
    }

    public void setAcceptedUsers(Set<User> acceptedUsers) {
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


    public Set<User> getAppliedUsers() {
        return appliedUsers;
    }

    public void setAppliedUsers(Set<User> appliedUsers) {
        this.appliedUsers = appliedUsers;
    }



    public void addAppliedUser(User user){
        this.getAppliedUsers().add(user);
    }

    public void addAcceptedUser(User user){
        this.getAcceptedUsers().add(user);
    }

    public PostState getPostState() {
        return postState;
    }

    public void setPostState(PostState postState) {
        this.postState = postState;
    }


    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}

