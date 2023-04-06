package it.unicollab.bh.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy="projects")
    private Set<User> members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    /**
     *
     * @param user
     */
    public void addMember(User user) {
        this.getMembers().add(user);
    }
}
