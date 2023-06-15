package it.unicollab.bh.repository;

import it.unicollab.bh.model.Profile;
import it.unicollab.bh.model.User;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {



    Profile findProfileByUser(User user);
}
