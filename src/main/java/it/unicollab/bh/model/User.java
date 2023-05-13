package it.unicollab.bh.model;

import it.unicollab.bh.model.oauth.AuthenticationProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;



@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;


    @Column(nullable = true, length = 64)

    private String Photos;
    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
    }

    @Email
    @Column(name = "email")
    String emailAddress;

    @Transient
    public String getPhotosImagePath() {
        if (Photos == null || id == null) return null;

        return "/user-photos/" + id + "/" + Photos;
    }

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime lastUpdateTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider oAuthProvider;


    /**
     * The profile associated to this user
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;
    
   /*@OneToOne(cascade = CascadeType.ALL)
    private Image picture;*/


	@ManyToOne
    private Course courseAttended;


    @ManyToMany
    private Set<Project> projects;


    /**
     * the owned posts
     */
    @OneToMany(mappedBy = "owner")
    private Collection<Post> ownedPosts;

    /**
     * the collection of  accepted post-applys
     */
    @ManyToMany(mappedBy = "acceptedUsers")
    private Collection<Post> acceptedApplies;

    @ManyToMany
    private Collection<Post> appliedPosts;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public AuthenticationProvider getoAuthProvider() {
        return oAuthProvider;
    }

    public void setoAuthProvider(AuthenticationProvider oAuthProvider) {
        this.oAuthProvider = oAuthProvider;
    }

    public Course getCourseAttended() {
        return courseAttended;
    }

    public void setCourseAttended(Course courseAttended) {
        this.courseAttended = courseAttended;
    }

    public Collection<Post> getOwnedPosts() {
        return ownedPosts;
    }

    public void setOwnedPosts(Collection<Post> ownedPosts) {
        this.ownedPosts = ownedPosts;
    }

    public Collection<Post> getAcceptedApplies() {
        return acceptedApplies;
    }

    public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setAcceptedApplies(Collection<Post> acceptedApplies) {
        this.acceptedApplies = acceptedApplies;
    }

    public Collection<Post> getAppliedPosts() {
        return appliedPosts;
    }

    public void setAppliedPosts(Collection<Post> appliedPosts) {
        this.appliedPosts = appliedPosts;
    }
}
