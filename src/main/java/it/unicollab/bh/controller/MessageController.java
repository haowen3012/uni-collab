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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@Controller
public class MessageController {

    @Autowired
    private SessionData sessionData;
    @Autowired
    private MessageService messageService;



    @RequestMapping(value="/sendReply/{sourceId}/{destId}/{postId}/{requestId}",  method = RequestMethod.POST)
    public String sendReplyMessage(@PathVariable("sourceId") Long idS,@PathVariable("destId") Long idD,
                                   @PathVariable("postId") Long idP,@PathVariable("requestId") Long idR,
                                   @ModelAttribute Message m){

        this.messageService.saveReplyMessage(idS,idD,idP,idR,m);

        return "redirect:/messages";
    }



    @RequestMapping(value="/sendRequest/{sourceId}/{destId}/{postId}",  method = RequestMethod.POST)
    public String sendReqeustMessage(@PathVariable("sourceId") Long idS, @PathVariable("destId") Long idD,
                                     @PathVariable("postId") Long idP,
                                     @ModelAttribute Message m, RedirectAttributes redirectAttributes){


    this.messageService.SaveRequestMessage(idS,idD,idP,m);

    redirectAttributes.addFlashAttribute("applied", true);

        return "redirect:/user";
    }



    @RequestMapping(value="/messages", method = RequestMethod.GET)
    public String showMessages(Model model) {

        User destination = this.sessionData.getLoggedUser();

        Collection<Message> messages = this.messageService.getAllMessageByDestination(destination);

        model.addAttribute("messages", messages);

        return "message.html";
    }


}
