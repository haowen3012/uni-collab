package it.unicollab.bh.authentication;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

    // ...

   @Override
    public void addViewControllers(ViewControllerRegistry registry) {

     //   registry.addRedirectViewController("/login","/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       exposeDirectory("profile-photos", registry);
   }
    
   private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
       Path uploadDir = Paths.get(dirName);
       String uploadPath = uploadDir.toFile().getAbsolutePath();
        
       if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
        
       registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
   }


}