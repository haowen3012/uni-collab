package it.unicollab.bh.controller.profile;

import it.unicollab.bh.configuration.FileUploadUtil;
import it.unicollab.bh.model.Profile;

import it.unicollab.bh.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/Profile/Image")
    public RedirectView saveProfile(Profile profile,
                                 @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        profile.setPhotos(fileName);

        Profile savedUser =  profileService.saveProfile(profile);


        String uploadDir = "profile-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/Profiles", true);
    }


}
