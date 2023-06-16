package it.unicollab.bh.service;


import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.PostState;
import it.unicollab.bh.model.Project.Project;
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

    @Autowired
    ProjectService projectService;

    @Transactional
    public Message saveMessage(Message message){

        return this.messageRepository.save(message);
    }

    @Transactional
    public Collection<Message> getAllMessageByDestination(User destination){

        return this.messageRepository.findAllByDestination(destination);
    }


    @Transactional
    public Collection<Message> getAllMessageByDestinationOrderByCreationTime(User destination){

        return this.messageRepository.findByDestinationOrderByCreationTimestampDesc(destination);
    }



    @Transactional
    public Message getMessage(Long id){
        return this.messageRepository.findById(id).get();
    }


    @Transactional
    public Collection<Message> getMessagesByPost(Post post){
        return this.messageRepository.findByPost(post);
    }




    @Transactional
    public void SaveRequestMessage(Long idSource,  Long idDestination,
                                  Long idPost, Message m){

        Post post = this.postService.getPost(idPost);
        User source = this.userService.getUser(idSource);

        post.addAppliedUser(source);
        source.getAppliedPosts().add(post);    // forse da modificare


        m.setSource(source);
        m.setDestination(this.userService.getUser(idDestination));
        m.setPost(post);


        this.userService.saveUser(source);
        this.postService.savePost(post);
        this.saveMessage(m);
    }

    @Transactional
    public Message saveReplyMessage( Long idSource, Long idDestination,
                                  Long idPost, Long idRequest,
                                   Message m){

        Post post = this.postService.getPost(idPost);
        User source = this.userService.getUser(idSource);
        User destination = this.userService.getUser(idDestination);

        if(post.getPostState()== PostState.EXPIRED){

            return  null ;
        }

        m.setSource(source);
        m.setDestination(destination);
        m.setPost(post);

        Message sourceRequestMessage = this.getMessage(idRequest);

        if(m.getMessageType()== MessageType.ACCEPT){
            sourceRequestMessage.setMessageType(MessageType.ACCEPTED);
            post.getAcceptedUsers().add(destination);
            source.getAcceptedApplies().add(post);

            this.postService.savePost(post);
            this.userService.saveUser(source);
        }

        if(m.getMessageType()==MessageType.DECLINE){
            sourceRequestMessage.setMessageType(MessageType.DECLINED);
        }

        this.saveMessage(sourceRequestMessage);
        return this.saveMessage(m);

    }


    @Transactional
    public void deleteAll(Collection<Message> messages){

        this.messageRepository.deleteAll(messages);
    }

}


