package it.unicollab.bh.service;


import it.unicollab.bh.model.Profile;

import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository  profileRepository;

    @Transactional
    public Profile getUser(long id) {
        Optional<Profile> result = this.profileRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Profile saveProfile(Profile profile){
        return this.profileRepository.save(profile);
    }

    @Transactional
    public List<Profile> getAllprofile(){
        List<Profile> result = new ArrayList<>();
        Iterable<Profile> iterable = this.profileRepository.findAll();
        for(Profile profile: iterable) result.add(profile);

        return result;

    }
}
