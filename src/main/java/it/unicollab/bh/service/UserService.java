package it.unicollab.bh.service;

import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User getUserById(long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }


    @Transactional
    public User getUserByUsername(String username){
        Optional<User> result = this.userRepository.findByUserName(username);
        return result.orElse(null);

    }

    @Transactional
    public User saveUser(User user){
        return this.userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsers(){
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user: iterable) result.add(user);

        return result;


    }
}
