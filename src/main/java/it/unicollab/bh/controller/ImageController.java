package it.unicollab.bh.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import it.unicollab.bh.model.Image;
import it.unicollab.bh.service.ImageService;

@Controller
public class ImageController {
	 @Autowired
	    private ImageService imageService;


	    @RequestMapping(method = RequestMethod.GET, path = "images/dinamic/{uid}")
	    public ResponseEntity<byte[]> getImage(@ModelAttribute(name = "uid") String uid)
	    {
	        Image temp = this.imageService.findById(uid);
	        if(temp != null)
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_TYPE, temp.getType())
	                    .body(temp.getData());
	        else
	            return ResponseEntity.notFound().build();

	    }
}
