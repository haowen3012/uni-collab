package it.unicollab.bh.service;

import it.unicollab.bh.model.*;
import it.unicollab.bh.model.message.Message;
import it.unicollab.bh.repository.MessageRepository;
import it.unicollab.bh.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private ExamService examService;

    @Autowired
    private MessageRepository messageRepository;


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
    public Collection<Post> getAllPostByOwnerOrderedByCreationTIme(User owner){

        return this.postRepository.findAllByOwnerOrderByCreationTimestampDesc(owner);
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


    /*returns the posts that you didn't crete, didn't applied and is not expired ( postState = active)*/
    @Transactional
    public Collection<Post> getHomePagePost(Course course, User owner, User appliedUser, PostState postState){
        return this.postRepository.findAllByOwnerCourseAttendedAndOwnerNotAndPostState( course,owner, postState);
    }

    @Transactional
    public Collection<Post> getHomePagePostOrderedByCreationTimeDesc(Course course, User owner,User appliedUser, PostState postState){

        return this.postRepository.findAllByOwnerCourseAttendedAndOwnerNotAndPostStateOrderByCreationTimestampDesc(
           course ,owner, postState);
    }


    @Transactional
    public Collection<Post> getHomePagePostFilteredByExam(Course course,User owner, User appliedUser, PostState postState, Exam exam){

        return this.postRepository.findAllByOwnerCourseAttendedAndOwnerNotAndPostStateAndExam(course, owner,postState,exam);
    }
    @Transactional
    public void deletePost(Long id){

        Post post = this.getPost(id);

        post.getOwner().getOwnedPosts().remove(post); // remove the post from the owner side

        for(User user : post.getAcceptedUsers()){   // remove the post from the accepted  users side

            user.getAcceptedApplies().remove(post);
        }

        for(User user : post.getAppliedUsers()){ // remove the post from the applied users side

            user.getAppliedPosts().remove(post);
        }

        Collection<Message> messages = this.messageRepository.findByPost(post);

        this.messageRepository.deleteAll(messages);


        this.postRepository.delete(post);

    }

    @Transactional
    public void deleteAll(Collection<Post> posts){
        this.postRepository.deleteAll(posts);

    }
    @Transactional
    public Collection<Post> deleteExpiredPosts(LocalDateTime localDateTime){

       return  this.postRepository.deleteByDeadlineBefore(localDateTime);
    }

    @Transactional
    public Post updatePost(Long idOldPost, Post newPost, Long idExam ){


        Post oldPost = this.getPost(idOldPost);

        BeanUtils.copyProperties(newPost, oldPost, new String[]{"id","owner","creationTimestamp","postState"});

        oldPost.setExam(this.examService.getExam(idExam));


        this.savePost(oldPost);

        return oldPost;

    }

    @Transactional
    public Post createPost(Post post){

        post.setPostState(PostState.ACTIVE);
        this.savePost(post);

        return post;
    }
}
