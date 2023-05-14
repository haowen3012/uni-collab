package it.unicollab.bh.model;

import jakarta.persistence.*;

import java.io.File;
import java.util.List;


@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true, length = 64)

    private String Photos;
    public String getPhotos() {
        return Photos;
    }

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





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
}
