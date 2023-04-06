package it.unicollab.bh.repository;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.service.CredentialsService;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
}
