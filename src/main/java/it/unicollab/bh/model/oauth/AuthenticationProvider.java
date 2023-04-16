package it.unicollab.bh.model.oauth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.net.http.HttpRequest;

public enum AuthenticationProvider {

    LOCAL, GITHUB


}