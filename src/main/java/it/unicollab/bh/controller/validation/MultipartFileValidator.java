package it.unicollab.bh.controller.validation;

import it.unicollab.bh.FileUploadWrapper;
import it.unicollab.bh.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MultipartFileValidator implements Validator {





    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadWrapper.class.equals(clazz);
    } // specifica la classe su cui opera il validator

    @Override
    public void validate(Object o, Errors errors) {

        FileUploadWrapper fileUploadWrapper = (FileUploadWrapper) o;


        if( !fileUploadWrapper.getImage().isEmpty() && !fileUploadWrapper.getImage().isEmpty() &&  !fileUploadWrapper.getImage().getOriginalFilename().endsWith(".png") &&
                !fileUploadWrapper.getImage().getOriginalFilename().endsWith(".jpg") &&
                !fileUploadWrapper.getImage().getOriginalFilename().endsWith(".jpeg")
        ){

            errors.rejectValue("image","invalidFormat.fileUploadWrapper.image");

        }


        if( !fileUploadWrapper.getBackground().isEmpty() && !fileUploadWrapper.getBackground().isEmpty() &&  !fileUploadWrapper.getBackground().getOriginalFilename().endsWith(".png") &&
                !fileUploadWrapper.getBackground().getOriginalFilename().endsWith(".jpg")
                && !fileUploadWrapper.getBackground().getOriginalFilename().endsWith(".jpeg")
        ){

            errors.rejectValue("background","invalidFormat.fileUploadWrapper.background");

        }



        if( !fileUploadWrapper.getCurriculum().isEmpty() && !fileUploadWrapper.getCurriculum().isEmpty() && !fileUploadWrapper.getCurriculum().getOriginalFilename().endsWith(".pdf")){

            errors.rejectValue("curriculum","invalidFormat");

        }


    }
}