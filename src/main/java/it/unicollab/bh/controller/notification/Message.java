package it.unicollab.bh.controller.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl;


public class Message  {



    private String text;

    private String destination;


    public void setText(String text) {
        this.text = text;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
