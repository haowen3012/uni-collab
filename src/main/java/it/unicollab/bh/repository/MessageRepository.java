package it.unicollab.bh.repository;

import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MessageRepository  extends CrudRepository<Message, Long> {


     Collection<Message> findAllByDestination(
             User destination);
}
