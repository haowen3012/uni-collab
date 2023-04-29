package it.unicollab.bh.controller.session;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.oauth.CustomOAuth2User;
import it.unicollab.bh.repository.CredentialsRepository;
import it.unicollab.bh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    private User user;

    private Credentials credentials;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UserRepository userRepository;


    public Credentials getLoggedCredentials(){

        this.update();

        return this.credentials;
    }

    public User getLoggedUser(){

        this.update();

        return this.user;
    }

    public User getLoggedOAuth2User(){

        this.oauth2Update();
        return this.user;
    }


    private void update(){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUserDetails = (UserDetails) object;

        this.credentials = this.credentialsRepository.findByUserName(loggedUserDetails.getUsername()).get();
        this.credentials.setPassword("[PROTECTED]");
        this.user = this.credentials.getUser();


    }

  private void   oauth2Update(){
        Object object = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User loggedOAuth2User = (CustomOAuth2User) object;

        this.user = userRepository.findByUserName(loggedOAuth2User.getLogin()).get();
    }

}
