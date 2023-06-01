package it.unicollab.bh.controller;

<<<<<<< HEAD

import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.User;
import it.unicollab.bh.service.ProfileService;
import it.unicollab.bh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController {



    @Autowired
    private SessionData sessionData;



    @RequestMapping(value={"/profile"}, method = RequestMethod.GET)
    public String getUserProfile(Model model){

        User loggedUser = this.sessionData.getLoggedUser();

        model.addAttribute("profile",loggedUser.getProfile());

        model.addAttribute("owner",loggedUser );

        return "profile.html";
    }
=======
import it.unicollab.bh.configuration.FileUploadUtil;
import it.unicollab.bh.controller.session.SessionData;
import it.unicollab.bh.model.Profile;

import it.unicollab.bh.model.User;
import it.unicollab.bh.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class ProfileController {
    @Autowired
    private SessionData sessionData;
    @Autowired
    ProfileService profileService;



    @RequestMapping(value={"profile/Image"}, method = RequestMethod.POST)
    public RedirectView saveProfile(Profile profile,
                                 @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        profile.setPhotos(fileName);

        Profile savedUser =  profileService.saveProfile(profile);


        String uploadDir = "profile-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new RedirectView("/Profiles", true);

}
>>>>>>> fcf9c6c9eb00c3750f393ed3b47701cf9b204ccd
}
