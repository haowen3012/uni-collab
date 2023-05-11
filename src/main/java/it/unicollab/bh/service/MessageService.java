package it.unicollab.bh.service;


import it.unicollab.bh.model.Message;
import it.unicollab.bh.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Transactional
    public Message saveMessage(Message message){

        return this.messageRepository.save(message);
    }

    @Transactional
    public Collection<Message> getAllMessageByDestination(String destination){

        return this.messageRepository.findAllByDestination(destination);
    }


}

