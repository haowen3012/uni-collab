package it.unicollab.bh.repository;

import it.unicollab.bh.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository  extends CrudRepository<Message, Long> {


}
