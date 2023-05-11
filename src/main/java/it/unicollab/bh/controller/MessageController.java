package it.unicollab.bh.controller;

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Message;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.MessageRepository;
import it.unicollab.bh.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;

@Controller
public class MessageController {

    @Autowired
    private SessionData sessionData;
    @Autowired
    private MessageService messageService;


    @RequestMapping(value="/sendApplyMessage", method = RequestMethod.POST)
    public String sendApplyMessage(@ModelAttribute Message m, Model model){

        messageService.saveMessage(m);


        return "redirect:/user";
    }


    @RequestMapping(value="/messages", method = RequestMethod.GET)
    public String showMessages(Model model){

        String destination = this.sessionData.getLoggedUser().getUserName();

        System.out.println(destination);

        Collection<Message>  messages = this.messageService.getAllMessageByDestination(destination);

        System.out.println(messages.toString());
        model.addAttribute("messages",messages);

        return "message.html";
    }
}
