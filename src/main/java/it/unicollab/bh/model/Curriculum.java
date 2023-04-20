package it.unicollab.bh.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.ValueGenerationType;

@Entity
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
