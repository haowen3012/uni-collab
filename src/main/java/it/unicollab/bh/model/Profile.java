package it.unicollab.bh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500)
    private String personalInformation;


    @Column(name="address")
    private String physicalAddress;

    @Email
    @Column(name = "email")
    private String emailAddress;



    @OneToOne
    private File image;

    @OneToOne
    private File background;

    @OneToOne
    private File curriculum;


    @OneToOne(mappedBy = "profile")
    private User user;


    public Profile(){

    }

    public Profile(String emailAddress){

        this.emailAddress= emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(String personalInformation) {
        this.personalInformation = personalInformation;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public File getBackground() {
        return background;
    }

    public void setBackground(File background) {
        this.background = background;
    }

    public File getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(File curriculum) {
        this.curriculum = curriculum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
