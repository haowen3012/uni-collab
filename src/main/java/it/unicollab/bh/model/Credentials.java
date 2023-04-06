package it.unicollab.bh.model;

import jakarta.persistence.*;

@Entity
public class Credentials {

    public static final String DEFAUL_ROLE = "DEFAULT";
    public static final String ADMIN_ROLE = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,length = 10)
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

}
