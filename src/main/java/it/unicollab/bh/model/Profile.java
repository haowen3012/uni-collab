package it.unicollab.bh.model;

import jakarta.persistence.*;


@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
