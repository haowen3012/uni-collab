package it.unicollab.bh.service;


import it.unicollab.bh.model.File;
import it.unicollab.bh.model.Profile;

import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.FileRepository;
import it.unicollab.bh.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;


    @Autowired
    private FileRepository fileRepository;


    @Transactional
    public Profile getProfile(Long id){
        return this.profileRepository.findById(id).get();
    }
    @Transactional
    public Profile saveProfile(Profile profile) {
        return this.profileRepository.save(profile);
    }


    @Transactional
    public Profile updatePersonalInformation(Long id, String pf){

        Profile profile = this.getProfile(id);

        profile.setPersonalInformation(pf);

        this.saveProfile(profile);

        return profile;
    }

    @Transactional
    public Profile updateProfileImages(Long id, MultipartFile img, MultipartFile bg, String email ,String address) {

        Profile profile = this.getProfile(id);


        if(email != null){
            profile.setEmailAddress(email);
        }

        if(address != null){
            profile.setPhysicalAddress(address);
        }

            try {
            if(!img.isEmpty()) {

              File oldImage = profile.getImage();


              if(oldImage==null){
                  File image = this.fileRepository.save(new File(img.getOriginalFilename(), img.getBytes()));
                  profile.setImage(image);

              }else{

                  oldImage.setName(img.getOriginalFilename());
                  oldImage.setBytes(img.getBytes());
                  this.fileRepository.save(oldImage);
              }


            }

            if(!bg.isEmpty()) {

                File oldBackground = profile.getBackground();

                if(oldBackground==null) {
                    File background = this.fileRepository.save(new File(bg.getOriginalFilename(), bg.getBytes()));
                    profile.setBackground(background);

                }else{

                    oldBackground.setName(bg.getOriginalFilename());
                    oldBackground.setBytes(bg.getBytes());
                    this.fileRepository.save(oldBackground);
                }
            }

            this.saveProfile(profile);


            } catch (IOException e) {


            }


        return profile;
    }


    @Transactional
    public Profile updateProfileCurriculum(Long idProfile, MultipartFile curri){

        Profile profile = this.getProfile(idProfile);

       try{
           if(curri!= null){
               File curriculum = this.fileRepository.save(new File(curri.getName(), curri.getBytes()));
               profile.setCurriculum(curriculum);

           }

           this.saveProfile(profile);
       }catch (IOException e){

       }
        return profile;
    }
}