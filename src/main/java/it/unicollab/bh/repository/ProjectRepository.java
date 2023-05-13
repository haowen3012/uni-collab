package it.unicollab.bh.repository;

import it.unicollab.bh.model.Project.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
