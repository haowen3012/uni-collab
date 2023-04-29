package it.unicollab.bh.repository;

import it.unicollab.bh.model.University;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends CrudRepository<University, Long> {

}
