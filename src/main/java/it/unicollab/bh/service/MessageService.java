package it.unicollab.bh.service;


import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import it.unicollab.bh.model.message.MessageType;
import it.unicollab.bh.repository.MessageRepository;
import it.unicollab.bh.repository.PostRepository;
import it.unicollab.bh.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Transactional
    public Message saveMessage(Message message){

        return this.messageRepository.save(message);
    }

    @Transactional
    public Collection<Message> getAllMessageByDestination(User destination){

        return this.messageRepository.findAllByDestination(destination);
    }


    @Transactional
    public Message getMessage(Long id){
        return this.messageRepository.findById(id).get();
    }



    @Transactional
    public void SaveRequestMessage(Long idSource,  Long idDestination,
                                  Long idPost, Message m){

        Post post = this.postService.getPost(idPost);
        User source = this.userService.getUser(idSource);

        post.addAppliedUser(source);
        source.addAppliedPost(post);    // forse da modificare


        m.setSource(source);
        m.setDestination(this.userService.getUser(idDestination));
        m.setPost(post);


        this.userService.saveUser(source);
        this.postService.savePost(post);
        this.saveMessage(m);
    }

    @Transactional
    public void saveReplyMessage( Long idSource, Long idDestination,
                                  Long idPost, Long idRequest,
                                   Message m){

        Post post = this.postService.getPost(idPost);
        User source = this.userService.getUser(idSource);
        User destination = this.userService.getUser(idDestination);

        m.setSource(source);
        m.setDestination(destination);
        m.setPost(post);

        Message sourceRequestMessage = this.getMessage(idRequest);

        if(m.getMessageType()== MessageType.ACCEPT){
            sourceRequestMessage.setMessageType(MessageType.ACCEPTED);
            post.getAcceptedUsers().add(destination);
            source.addAcceptedApply(post);

            this.postService.savePost(post);
            this.userService.saveUser(source);
        }

        if(m.getMessageType()==MessageType.DECLINE){
            sourceRequestMessage.setMessageType(MessageType.DECLINED);
        }

        this.saveMessage(sourceRequestMessage);
        this.saveMessage(m);

    }

}

