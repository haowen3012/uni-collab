package it.unicollab.bh;

import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.PostState;
import it.unicollab.bh.model.Project.Project;
import it.unicollab.bh.model.Project.ProjectState;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.message.Message;
import it.unicollab.bh.model.message.MessageType;
import it.unicollab.bh.service.MessageService;
import it.unicollab.bh.service.PostService;
import it.unicollab.bh.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Transactional
    public void createProject(){
      Collection<Post> fullmembersPosts = postService.getPostByMembershipEqualsAcceptedUsersSize();

      if(fullmembersPosts == null) return;

      for (Post post: fullmembersPosts) {

        post.addAcceptedUser(post.getOwner()); // add the post owner to the acceptedUser set

        /*create a new Project from each of these full members posts*/
        Project project = new Project(post.getProjectName(), post.getProjectDescription(), ProjectState.ACTIVE);

        this.projectService.saveProject(project);

        post.setPostState(PostState.EXPIRED);

        for(User user : post.getAcceptedUsers()){

          this.projectService.shareProjectWithUser(project,user);

          /*send a message to each memeber*/
          messageService.saveMessage(new Message(null, user ,"a new Project is active",post,project, MessageType.PROJECT_ACTIVE));
        }


         /*delete these posts*/
      }


    }


   @Scheduled(fixedRate = 10000)
   @Transactional
    public void sendPostExpirationMessage() {

      Collection<Post> expiredPosts = this.postService.getExpiredPosts(LocalDateTime.now(),PostState.EXPIRED);


      if(expiredPosts == null) return;


      for (Post post : expiredPosts) {
        post.setPostState(PostState.EXPIRED);
        post.addAcceptedUser(post.getOwner());   // add the post owner to the acceptedUser set

        for (User user : post.getAcceptedUsers()) {

          messageService.saveMessage(new Message(null, user, "is expired", post, null , MessageType.POST_EXPIRED));
        }

      }
    }


  @Scheduled(fixedRate = 10000)
  @Transactional
  public void terminateProject(){

      Collection<Project> terminateProjects = this.projectService.getTerminateProjects(LocalDateTime.now(), ProjectState.TERMINATED);

      if(terminateProjects == null)  return;


      for(Project project : terminateProjects){
        project.setProjectState(ProjectState.TERMINATED);


        for (User user : project.getMembers() ) {

          messageService.saveMessage(new Message(null, user, "is terminated", null, project , MessageType.PROJECT_TERMINATED));
        }
      }


  }

}
