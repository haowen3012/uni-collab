package it.unicollab.bh.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.util.List;

@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "university")
    private List<Course>  courses;


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


}
