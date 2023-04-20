package it.unicollab.bh.controller.session;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    private User user;

    private Credentials credentials;

    @Autowired
    private CredentialsRepository credentialsRepository;


    public Credentials getLoggedCredentials(){

        this.update();

        return this.credentials;
    }

    public User getLoggedUser(){

        this.update();

        return this.user;
    }

    private void update(){
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUserDetails = (UserDetails) object;

        this.credentials = this.credentialsRepository.findByUserName(loggedUserDetails.getUsername()).get();
        this.credentials.setPassword("[PROTECTED]");
        this.user = this.credentials.getUser();


    }

 /*   private void oauthUpdate(){
        Object object = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User customOAuth2User = (OAuth2User) object;

        this.credentials
    }
*/
}
