package it.unicollab.bh.controller;

import it.unicollab.bh.model.Message;
import it.unicollab.bh.repository.MessageRepository;
import it.unicollab.bh.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;


    @RequestMapping(value="/sendApplyMessage", method = RequestMethod.POST)
    public String sendApplyMessage(@ModelAttribute Message m, Model model){

        messageService.saveMessage(m);

        return "createPost.html";
    }
}
