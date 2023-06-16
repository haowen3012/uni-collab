package it.unicollab.bh.model.oauth;

import it.unicollab.bh.model.User;
import it.unicollab.bh.repository.UserRepository;
import it.unicollab.bh.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler  implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    public OAuth2LoginSuccessHandler(){
         super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
    		throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
       
        String loginName = oAuth2User.getLogin();
        String displayName = oAuth2User.getName();
        String email = oAuth2User.getEmail();
        String fullName = oAuth2User.getFullName();
        System.out.println("Login name: " + loginName );
        System.out.println("Display name: " + displayName );
        System.out.println("email: " + email );
        System.out.println("fullname: " + fullName );


        User user;
        if(loginName != null) {

            user = userService.getUser(loginName);
        }else{

            user = userService.getUser(fullName);
        }




        if(user == null){
            userService.registerNewCustomerAfterOAuthLoginSuccess(loginName,fullName, email,AuthenticationProvider.OAUTH);
        }else{
            userService.updateExistingUser(user, fullName, AuthenticationProvider.OAUTH);
        }

        response.sendRedirect("/login/oauth2/user");
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    
    }
