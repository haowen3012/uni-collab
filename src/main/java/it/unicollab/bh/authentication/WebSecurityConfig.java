package it.unicollab.bh.authentication;


import it.unicollab.bh.model.Course;
import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.model.University;
import it.unicollab.bh.model.User;
import it.unicollab.bh.model.oauth.OAuth2LoginSuccessHandler;
import it.unicollab.bh.repository.CredentialsRepository;
import it.unicollab.bh.service.CourseService;
import it.unicollab.bh.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import java.util.ArrayList;

import static it.unicollab.bh.model.Credentials.ADMIN_ROLE;
import static it.unicollab.bh.model.Credentials.DEFAULT_ROLE;
import static javax.management.Query.and;

@Configuration
@EnableWebSecurity
public  class WebSecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;


    @Autowired
    private CredentialsRepository credentialsRepository;  // questo lo toglieremo alla fine







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
        return (web) -> web.ignoring().requestMatchers("/resources/**");
    }*/

    
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
                .requestMatchers("","/css/**", "/script/**").permitAll()
                .requestMatchers("/oauth2/**").authenticated()
                .requestMatchers(HttpMethod.GET,"/","/index","/user/register").permitAll()
                .requestMatchers(HttpMethod.POST,"/user/register").permitAll()
                .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/")
                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/successful")
                .failureUrl("/login?error=true")
                .and().logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true).permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService( customOAuth2UserService)
                .and()
               .successHandler(oAuth2LoginSuccessHandler)
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true).permitAll();
                return httpSecurity.build();
    }



  /*  @Bean
    public void createTestUser(){
        Credentials  testCreddentials = new Credentials();
        User testUser = new User();

        testUser.setUserName("Dottor Zheng");

        testUser.setFirstName("Haowen");
        testUser.setLastName("Zheng");

        testCreddentials.setUserName(testUser.getUserName());
        testCreddentials.setPassword(passwordEncoder().encode("haowenZheng"));
        testCreddentials.setRole(DEFAULT_ROLE);
        testCreddentials.setUser(testUser);

      /*  University university = new University("Roma Tre",new ArrayList<>());
        Course course= new Course("Ingegneria Informatica",university);

        university.getCourses().add(course);

        testUser.setCourseAttended( course);
        */
    /*
     this.credentialsRepository.save(testCreddentials);
    }
    */


}