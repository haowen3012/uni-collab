package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import it.unicollab.bh.model.message.MessageType;
import it.unicollab.bh.service.MessageService;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class MessageController {

    @Autowired
    private SessionData sessionData;
    @Autowired
    private MessageService messageService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @RequestMapping(value="/sendReply/{sourceId}/{destId}/{postId}/{requestId}",  method = RequestMethod.POST)
    public String sendReplyMessage(@PathVariable("sourceId") Long idS,@PathVariable("destId") Long idD,
                                   @PathVariable("postId") Long idP,@PathVariable("requestId") Long idR,
                                   @ModelAttribute Message m, Model model){

        m.setSource(this.userService.getUser(idS));
        m.setDestination(this.userService.getUser(idD));
        m.setPost(this.postService.getPost(idP));

        Message sourceRequestMessage = this.messageService.getMessage(idR);

        if(m.getMessageType()==MessageType.ACCEPT){
            sourceRequestMessage.setMessageType(MessageType.ACCEPTED);
            this.messageService.saveMessage(sourceRequestMessage);
        }

        if(m.getMessageType()==MessageType.DECLINE){
            sourceRequestMessage.setMessageType(MessageType.DECLINED);
            this.messageService.saveMessage(sourceRequestMessage);
        }

        this.messageService.saveMessage(m);


        return "redirect:/messages";
    }


    @Transactional  // questo metodo è da modificare
    @RequestMapping(value="/sendRequest/{sourceId}/{destId}/{postId}",  method = RequestMethod.POST)
    public String sendReqeustMessage(@PathVariable("sourceId") Long idS,@PathVariable("destId") Long idD,
                                     @PathVariable("postId") Long idP,
                                     @ModelAttribute Message m, Model model){

        Post post = this.postService.getPost(idP);
        User source = this.userService.getUser(idS);
        source.getAppliedPosts().add(post);    // forse da modificare


        m.setSource(source);
        m.setDestination(this.userService.getUser(idD));
        m.setPost(post);


        this.userService.saveUser(source);
        this.messageService.saveMessage(m);


        return "redirect:/user";
    }



    @RequestMapping(value="/messages", method = RequestMethod.GET)
    public String showMessages(Model model){

        User destination = this.sessionData.getLoggedUser();

        Collection<Message>  messages = this.messageService.getAllMessageByDestination(destination);

        model.addAttribute("messages",messages);

        return "message.html";
    }
}
