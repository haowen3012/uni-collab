package it.unicollab.bh.service;

import it.unicollab.bh.model.Profile;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.oauth.AuthenticationProvider;
import it.unicollab.bh.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;


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


    public void registerNewCustomerAfterOAuthLoginSuccess(String loginName, String fullName,String email,AuthenticationProvider provider) {

        User user = new User(new Profile(email));
        if(loginName != null) {
            user.setUserName(loginName);
            user.setFirstName(fullName);
        }
        else{
            user.setUserName(fullName);
        }

        user.setCreationTimestamp(LocalDateTime.now());
        user.setoAuthProvider(provider);
        

        userRepository.save(user);
    }

    public void updateExistingUser(User user, String fullName, AuthenticationProvider provider){
        user.setFirstName(fullName);
        user.setoAuthProvider(provider);   // probabilmente da modificare

        userRepository.save(user);
    }

}
