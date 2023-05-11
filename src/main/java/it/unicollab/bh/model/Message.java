package it.unicollab.bh.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl;


public class Message  {


   private String source;

    private String text;

    private String destination;


    


    public void setText(String text) {
        this.text = text;
    }

    public String getText(){ return text;}

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
