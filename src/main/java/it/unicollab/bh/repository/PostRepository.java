package it.unicollab.bh.repository;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PostRepository  extends CrudRepository<Post, Long> {


    public Collection<Post> findAllByOwner(User owner);
}
