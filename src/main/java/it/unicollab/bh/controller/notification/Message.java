package it.unicollab.bh.controller.notification;

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
