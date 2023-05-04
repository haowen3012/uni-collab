package it.unicollab.bh.repository;

import it.unicollab.bh.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository  extends CrudRepository<Post, Long> {

}
