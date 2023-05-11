package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import it.unicollab.bh.model.message.MessageType;
import it.unicollab.bh.service.MessageService;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.UserService;
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


    @RequestMapping(value="/sendApplyMessage/{sourceId}/{destId}/{postId}",  method = RequestMethod.POST)
    public String sendApplyMessage(@PathVariable("sourceId") Long idS,@PathVariable("destId") Long idD,
                                   @PathVariable("postId") Long idP,
                                   @ModelAttribute Message m, Model model){

        m.setSource(this.userService.getUser(idS));
        m.setDestination(this.userService.getUser(idD));
        m.setMessageType(MessageType.REQUEST);
        m.setPost(this.postService.getPost(idP));

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
