package it.unicollab.bh.service;

import it.unicollab.bh.model.Course;
import it.unicollab.bh.model.University;
import it.unicollab.bh.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;


    @Transactional
    public Course getCourse(Long id) {
        Optional<Course> result = this.courseRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public List<Course> getCourseByUniversity(University university){

        List<Course> result = this.courseRepository.findCoursesByUniversity(university);

        return result;
    }

}
