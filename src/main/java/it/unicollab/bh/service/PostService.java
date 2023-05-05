package it.unicollab.bh.service;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PostService {


    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Post savePost(Post post){
        return this.postRepository.save(post);
    }


    @Transactional
    public Collection<Post> getAllPost(){

        return (Collection<Post>)this.postRepository.findAll();
    }

    @Transactional
    public Collection<Post> getAllPostByOwner(User owner){

        return this.postRepository.findAllByOwner(owner);
    }
}
