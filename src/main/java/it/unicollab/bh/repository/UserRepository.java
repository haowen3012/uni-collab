package it.unicollab.bh.repository;

import it.unicollab.bh.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepositoryExtensionsKt;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findByUserName(String username);
}
