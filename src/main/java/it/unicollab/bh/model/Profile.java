package it.unicollab.bh.model;

import jakarta.persistence.*;

import java.io.File;


@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private File profileImage;

    @OneToOne
    private Curriculum curriculum;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(File profileImage) {
        this.profileImage = profileImage;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
