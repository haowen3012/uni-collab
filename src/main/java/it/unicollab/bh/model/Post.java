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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
