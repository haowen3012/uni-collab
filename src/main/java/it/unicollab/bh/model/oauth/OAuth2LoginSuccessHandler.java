package it.unicollab.bh.model.oauth;

import it.unicollab.bh.model.User;
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
        System.out.println("Login name: " + loginName );
        System.out.println("Display name: " + displayName );


        User user = userService.getUser(loginName);

        if(user == null){
            userService.registerNewCustomerAfterOAuthLoginSuccess(loginName,displayName, AuthenticationProvider.GITHUB );
        }else{
            userService.updateExistingUser(user, displayName, AuthenticationProvider.GITHUB);
        }

        response.sendRedirect("/login/oauth2/user");
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
