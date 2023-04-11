package it.unicollab.bh.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static it.unicollab.bh.model.Credentials.ADMIN_ROLE;
import static it.unicollab.bh.model.Credentials.DEFAULT_ROLE;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    DataSource dataSource;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT user_name, role from credentials WHERE user_name=?")
                .usersByUsernameQuery("SELECT user_name, password, 1 as enabled FROM credentials WHERE user_name=?");
    }

/*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/**");
    }
*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{
      httpSecurity
              .csrf().and().cors().disable()
              .authorizeHttpRequests()
              .requestMatchers(HttpMethod.GET,"/","/index","/user/register").permitAll()
              .requestMatchers(HttpMethod.POST,"/user/register").permitAll()
              .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
              .requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
              .anyRequest().authenticated()
              .and().formLogin()
              .loginProcessingUrl("/login")
              .loginPage("/login")
              .permitAll()
              .defaultSuccessUrl("/user")
              .and().logout()
              .logoutUrl("/logout")
              .logoutSuccessUrl("/index")
              .invalidateHttpSession(true)
              .clearAuthentication(true).permitAll();


      return httpSecurity.build();
    }

}