package it.unicollab.bh.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
