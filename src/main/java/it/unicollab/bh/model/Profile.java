package it.unicollab.bh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

<<<<<<< HEAD
    @Column(length = 500)
    private String personalInformation;


    private String physicaladdress;

    @Email
    @Column(name = "email")
    private String emailAddress;



=======
    @Column(nullable = true, length = 2000)
    private String Photos;
    public String getPhotos() {
        return Photos;
    }

    public Profile() {
        this.user = user;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    public void setPhotos(String photos) {
        Photos = photos;
    }
    @Transient
    public String getPhotosImagePath() {
        if (Photos == null || id == null) return null;

        return "/profile-photos/" + id + "/" + Photos;
    }


    @OneToOne
    private Curriculum curriculum;
>>>>>>> fcf9c6c9eb00c3750f393ed3b47701cf9b204ccd

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

    public String getPhysicaladdress() {
        return physicaladdress;
    }

    public void setPhysicaladdress(String physicaladdress) {
        this.physicaladdress = physicaladdress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
