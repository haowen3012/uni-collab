package it.unicollab.bh.service;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.PostState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Post savePost(Post post){
        return this.postRepository.save(post);
    }


    @Transactional
    public Post getPost(Long id ){
        Optional<Post> result = this.postRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Collection<Post> getAllPost(){
        return (Collection<Post>)this.postRepository.findAll();
    }

    @Transactional
    public Collection<Post> getAllPostByOwner(User owner){

        return this.postRepository.findAllByOwner(owner);
    }

    @Transactional
    public Collection<Post> getAllPostByOwnerNot(User owner){
         return this.postRepository.findAllByOwnerNot(owner);
    }


    @Transactional
    public Collection<Post> getPostsByOwnerNotAndAppliedUsersNotContaining(User owner, User appliedUser){
        return this.postRepository.findAllByOwnerNotAndAppliedUsersNotContaining(owner, appliedUser);
    }

    @Transactional
    public Collection<Post> getPostByMembershipEqualsAcceptedUsersSize(){

        return this.postRepository.findAllByMembershipEqualsAcceptedUsersSize();
    }

    @Transactional
    public Collection<Post> getExpiredPosts(LocalDateTime localDateTime, PostState postState){

        return this.postRepository.findByDeadlineBeforeAndPostStateNot(localDateTime, postState);
    }

    @Transactional
    public void deletePost(Long id){
        this.postRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll(Collection<Post> posts){
        this.postRepository.deleteAll(posts);

    }
    @Transactional
    public Collection<Post> deleteExpiredPosts(LocalDateTime localDateTime){

       return  this.postRepository.deleteByDeadlineBefore(localDateTime);
    }
}
