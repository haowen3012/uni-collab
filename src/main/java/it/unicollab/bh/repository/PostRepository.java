package it.unicollab.bh.repository;

import it.unicollab.bh.model.*;
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


    Collection<Post> findAllByOwnerCourseAttendedAndOwnerNotAndAppliedUsersNotContainingAndPostState(Course course, User owner, User appliedUser, PostState postState);


    /*************++delete queries*******/

    Collection<Post> deleteByDeadlineBefore(LocalDateTime localDateTime);


    Collection<Post> findAllByOwnerCourseAttendedAndOwnerNotAndAppliedUsersNotContainingAndPostStateOrderByCreationTimestampDesc(Course course,User owner, User appliedUser, PostState postState);


    /*trova tutti i post di cui l'utente loggato non è proprietario, a cui non si è candidato, che sono ancora attivi e che si riferiscono ad un certo esame*/
    Collection<Post>  findAllByOwnerCourseAttendedAndOwnerNotAndAppliedUsersNotContainingAndPostStateAndExam( Course course, User owner, User appliedUser, PostState postState, Exam exam);

}
