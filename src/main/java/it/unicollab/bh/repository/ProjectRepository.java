package it.unicollab.bh.repository;

import it.unicollab.bh.model.PostState;
import it.unicollab.bh.model.Project.Project;
import it.unicollab.bh.model.Project.ProjectState;
import it.unicollab.bh.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ProjectRepository extends CrudRepository<Project, Long> {


    Collection<Project> findByMembersContains(User member);

    Collection<Project> findByMembersContainsOrderByCreationTimestampDesc(User member);


    Collection<Project>   findByDeadlineBeforeAndProjectStateNot(LocalDateTime localDateTime, ProjectState postState);

}
