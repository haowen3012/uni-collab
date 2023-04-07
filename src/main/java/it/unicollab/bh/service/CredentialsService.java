package it.unicollab.bh.service;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.repository.CredentialsRepository;
import jakarta.transaction.Transactional;
import org.hibernate.boot.jaxb.cfg.spi.JaxbCfgMappingReferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;

    /**
     *
     * @param id
     * @return the retrieved Credentials, or null if no Credentials with the passed id could be found
     */
    @Transactional
    public Credentials getCredentials(long id){
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    /**
     *
     * @param username
     * @return the retrieved Credentials, or null if no Credentials with the passed username could be found
     */
    @Transactional
    public Credentials getCredentials(String username){
        Optional<Credentials> result = this.credentialsRepository.findByUserName(username);
        return result.orElse(null);
    }

    /**
     *
     * @param credentials
     * @return the saved Credentials
     * @throws org.springframework.dao.DataIntegrityViolationException if a Credentials with the same username ass the passed Credentials
     *                                                                  already exists in the DB
     */
    @Transactional
    public Credentials saveCredentials(Credentials credentials){
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }


}
