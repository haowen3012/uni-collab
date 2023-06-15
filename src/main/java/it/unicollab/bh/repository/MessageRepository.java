package it.unicollab.bh.repository;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.hibernate.annotations.CollectionId;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MessageRepository  extends CrudRepository<Message, Long> {


     Collection<Message> findAllByDestination(
             User destination);


     Collection<Message> findByDestinationOrderByCreationTimestamp(User destination);

     Collection<Message> findByPost(Post post);
}

