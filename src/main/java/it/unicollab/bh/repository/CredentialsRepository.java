package it.unicollab.bh.repository;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.service.CredentialsService;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

    /**
     *
     * @param username
     * @return an Optional for the Credentials with the passed username
     */
    public Optional<Credentials>  findByUserName(String username);

}
