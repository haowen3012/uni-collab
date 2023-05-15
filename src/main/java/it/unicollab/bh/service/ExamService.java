package it.unicollab.bh.service;


import it.unicollab.bh.model.Exam;
import it.unicollab.bh.repository.ExamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {


    @Autowired
    private ExamRepository examRepository;



    @Transactional
    public Exam getExam(Long id){
        return this.examRepository.findById(id).get();
    }
}
