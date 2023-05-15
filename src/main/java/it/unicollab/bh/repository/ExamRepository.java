package it.unicollab.bh.repository;

import it.unicollab.bh.model.Course;
import it.unicollab.bh.model.Exam;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ExamRepository  extends CrudRepository<Exam, Long> {



    Collection<Exam> findExamByCourse(Course couse);

}
