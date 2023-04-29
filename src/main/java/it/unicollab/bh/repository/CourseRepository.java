package it.unicollab.bh.repository;


import it.unicollab.bh.model.Course;
import it.unicollab.bh.model.University;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    public List<Course> findCoursesByUniversity(University university);
}
