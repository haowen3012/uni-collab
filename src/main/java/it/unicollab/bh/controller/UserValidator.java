package it.unicollab.bh.controller;

import com.fasterxml.jackson.databind.type.ClassStack;
import it.unicollab.bh.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.swing.*;

@Component
public class UserValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;
        String firstName = user.getFirstName().trim();
        String lastName = user.getLastName().trim();

        if(firstName.isBlank()){
            errors.rejectValue("firstname","required");
        }
        else if(firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_NAME_LENGTH){
            errors.rejectValue("firstName","size");
        }

        if(lastName.isBlank()){
            errors.rejectValue("lastname","required");
        }
        else if(firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_NAME_LENGTH){
            errors.rejectValue("lastName","size");
        }

    }
}
