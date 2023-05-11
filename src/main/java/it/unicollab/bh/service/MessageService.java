package it.unicollab.bh.service;


import it.unicollab.bh.model.Message;
import it.unicollab.bh.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message saveMessage(Message message){

        return this.messageRepository.save(message);
    }
}
