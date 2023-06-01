package it.unicollab.bh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.File;
import java.util.List;


@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500)
    private String personalInformation;


    private String physicaladdress;

    @Email
    @Column(name = "email")
    private String emailAddress;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(String personalInformation) {
        this.personalInformation = personalInformation;
    }

    public String getPhysicaladdress() {
        return physicaladdress;
    }

    public void setPhysicaladdress(String physicaladdress) {
        this.physicaladdress = physicaladdress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
