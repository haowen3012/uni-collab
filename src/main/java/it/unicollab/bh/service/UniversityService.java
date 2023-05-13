package it.unicollab.bh.service;

import it.unicollab.bh.model.University;
import it.unicollab.bh.repository.UniversityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepository universityRepository;


    @Transactional
    public University getUniversity(Long id){
        Optional<University> result = this.universityRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public  List<University> getAllUniversities(){
        List<University> result = (List<University>)this.universityRepository.findAll();
        return result;
    }
}
