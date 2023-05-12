package it.unicollab.bh.service;


import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import it.unicollab.bh.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Collection<Message> getAllMessageByDestination(User destination){

        return this.messageRepository.findAllByDestination(destination);
    }


    @Transactional
    public Message getMessage(Long id){
        return this.messageRepository.findById(id).get();
    }

}

