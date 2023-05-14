package it.unicollab.bh.repository;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.PostState;
import it.unicollab.bh.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PostRepository  extends CrudRepository<Post, Long> {


     Collection<Post> findAllByOwner(User owner);

     Collection<Post>  findAllByOwnerNot(User owner);

     Collection<Post> findAllByOwnerNotAndAppliedUsersNotContaining(User owner,User appliedUser);

    @Query("SELECT p FROM Post p WHERE p.membership = SIZE(p.acceptedUsers) AND p.postState='ACTIVE' ")
    Collection<Post> findAllByMembershipEqualsAcceptedUsersSize();

    Collection<Post> findByDeadlineBeforeAndPostStateNot(LocalDateTime localDateTime, PostState postState);


    Collection<Post> findAllByOwnerNotAndAppliedUsersNotContainingAndPostState(User owner, User appliedUser, PostState postState);

    /*************++delete queries*******/

    Collection<Post> deleteByDeadlineBefore(LocalDateTime localDateTime);


    Collection<Post> findAllByOwnerNotAndAppliedUsersNotContainingAndPostStateOrderByCreationTimestampDesc(User owner, User appliedUser, PostState postState);

}
