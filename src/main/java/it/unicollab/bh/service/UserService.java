package it.unicollab.bh.service;

import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User getUser(long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }


    @Transactional
    public User getUser(String username){
        Optional<User> result = this.userRepository.findByUserName(username);
        return result.orElse(null);

    }

    /**
     *
     * @param user
     * @return the saved user
     * @throws org.springframework.dao.DataIntegrityViolationException if a User with the same username as the passed User already existed in the DB
     */
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
