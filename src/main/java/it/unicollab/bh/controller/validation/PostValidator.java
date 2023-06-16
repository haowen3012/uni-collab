package it.unicollab.bh.controller.validation;

import com.fasterxml.jackson.databind.type.ClassStack;
import it.unicollab.bh.model.Post;
import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import javax.swing.*;
import java.time.LocalDateTime;

@Component
public class PostValidator implements Validator {

    private boolean updating = false;

    @Autowired
    private PostRepository postRepository;


    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Post.class.equals(clazz);
    } // specifica la classe su cui opera il validator

    @Override
    public void validate(Object o, Errors errors) {

        Post post = (Post) o;

        if (!updating && post.getProjectName() !=null &&  post.getExam() != null
                && postRepository.existsByProjectNameAndExam(post.getProjectName(), post.getExam())) {
            errors.reject("post.duplicate");

        }

        if(post.getExam()==null){
            errors.rejectValue("exam", "required");
        }

        if( post.getDeadline()!=null && post.getDeadline().isBefore(LocalDateTime.now())){

            errors.rejectValue("deadline", "invalidLocalDateTime");
        }

    }
}
