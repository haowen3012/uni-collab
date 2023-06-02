package it.unicollab.bh.controller;

import it.unicollab.bh.model.File;
import it.unicollab.bh.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class FileController {

    @Autowired
    private FileRepository fileRepository;




    @GetMapping("/display/image/{id}")
    public ResponseEntity<byte[]> displayItemImage(@PathVariable("id") Long id) {
        File img = this.fileRepository.findById(id).get();
        byte[] image = img.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }


    @GetMapping("/display/file/{id}")
    public ResponseEntity<byte[]> displayItemFile(@PathVariable("id") Long id) {

        File f = this.fileRepository.findById(id).get();
        byte[] file = f.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment",  "curriculum.pdf");

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }
}
