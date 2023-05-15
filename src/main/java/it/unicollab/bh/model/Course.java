package it.unicollab.bh.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private University university;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Exam> courseExams;


    public Course() {

    }

    public Course(String name, University university) {
        this.name = name;
        this.university = university;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Set<Exam> getCourseExams() {
        return courseExams;
    }

    public void setCourseExams(Set<Exam> courseExams) {
        this.courseExams = courseExams;
    }
}