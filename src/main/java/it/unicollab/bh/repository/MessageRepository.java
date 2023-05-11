package it.unicollab.bh.repository;

import it.unicollab.bh.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MessageRepository  extends CrudRepository<Message, Long> {


     Collection<Message> findAllByDestination(String destination);
}
