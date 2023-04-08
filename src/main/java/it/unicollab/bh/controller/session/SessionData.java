package it.unicollab.bh.controller.session;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

    private User user;

    private Credentials credentials;

    @Autowired
    private CredentialsRepository credentialsRepository;


    public Credentials getLoggedCredentials(){
        if(this.credentials == null) {
        }
        return this.credentials;
    }


}
