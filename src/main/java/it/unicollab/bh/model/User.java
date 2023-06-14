package it.unicollab.bh.model;

import it.unicollab.bh.model.Project.Project;
import it.unicollab.bh.model.oauth.AuthenticationProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
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

	@ManyToOne
    private Course courseAttended;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Project> projects;


    /**
     * the owned posts
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Post> ownedPosts;

    /**
     * the collection of  accepted post-applys
     */
    @ManyToMany(mappedBy = "acceptedUsers")
    private Set<Post> acceptedApplies;

    @ManyToMany
    private Set<Post> appliedPosts;


   public User(){

   }

   public User(Profile profile){

       this.profile = profile;
   }

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

    public Set<Post> getOwnedPosts() {
        return ownedPosts;
    }

    public void setOwnedPosts(Set<Post> ownedPosts) {
        this.ownedPosts = ownedPosts;
    }

    public Set<Post> getAcceptedApplies() {
        return acceptedApplies;
    }


	public void setAcceptedApplies(Set<Post> acceptedApplies) {
        this.acceptedApplies = acceptedApplies;
    }

    public Set<Post> getAppliedPosts() {
        return appliedPosts;
    }

    public void setAppliedPosts(Set<Post> appliedPosts) {
        this.appliedPosts = appliedPosts;
    }




    /********************************************************/

    public void addProject(Project project){
        this.getProjects().add(project);
    }

    public void addAppliedPost(Post post){
        this.getAppliedPosts().add(post);
    }

    public void addAcceptedApply(Post post){
        this.getAcceptedApplies().add(post);
    }

    public void addOwnedPost(Post post) {

        this.getAcceptedApplies().add(post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
