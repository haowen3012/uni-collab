package it.unicollab.bh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    /**
     * The profile associated to this user
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;





}
