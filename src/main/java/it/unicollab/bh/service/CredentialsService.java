package it.unicollab.bh.service;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CredentialsService {

    @Autowired
    protected CredentialsRepository credentialsRepository;
}
