package it.unicollab.bh.model.message;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.Project.Project;
import it.unicollab.bh.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
public class Message  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;


    @ManyToOne
    private User source;


    @ManyToOne
    private  User  destination;


    @ManyToOne
    private Post post;

    @ManyToOne // forse si deve cambiare
    private Project project;


    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;


    public Message(){}

    public Message( User source, User destination, String text,Post post,Project project, MessageType messageType) {
        this.text = text;
        this.source = source;
        this.destination = destination;
        this.post = post;
        this.project= project;
        this.messageType = messageType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }

    public User getDestination() {
        return destination;
    }

    public void setDestination(User destination) {
        this.destination = destination;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}
