package it.unicollab.bh;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.Project;
import it.unicollab.bh.model.ProjectState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import it.unicollab.bh.model.message.MessageType;
import it.unicollab.bh.service.MessageService;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.ProjectService;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class ScheduledTasks {

  @Autowired
  PostService postService;

  @Autowired
  ProjectService projectService;

  @Autowired
  MessageService messageService;


    @Scheduled(fixedRate = 10000)
    public void createProject(){
      Collection<Post> fullmembersPosts = postService.getPostByMembershipEqualsAcceptedUsersSize();

      for (Post post: fullmembersPosts) {

        post.getAcceptedUsers().add(post.getOwner()); // add the post owner to the acceptedUser set

        /*create a new Project from each of these full members posts*/
        Project project = new Project(post.getProjectName(), post.getProjectDescription(), ProjectState.ACTIVE,
                post.getAcceptedUsers());


        for(User user : post.getAcceptedUsers()){

          this.projectService.shareProjectWithUser(project,user);

          /*send a message to each memeber*/
          messageService.saveMessage(new Message(null, user ,"a new Project is active",post, MessageType.PROJECT_ACTIVE));
        }


         /*delete these posts*/
         this.postService.deleteAll(fullmembersPosts);
      }


    }


    @Scheduled(fixedRate = 10000)
    public void deleteExpiredPosts() {

      Collection<Post> expiredPosts = this.postService.deleteExpiredPosts(LocalDateTime.now());


      for (Post post : expiredPosts) {
        post.getAppliedUsers().add(post.getOwner());   // add the post owner to the acceptedUser set

        for (User user : post.getAcceptedUsers()) {

          messageService.saveMessage(new Message(null, user, "The post is expired", post, MessageType.POST_EXPIRED));
        }

      }
    }

}
