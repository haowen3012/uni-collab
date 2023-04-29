package it.unicollab.bh.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    ManyToOne
    private University university;

}
