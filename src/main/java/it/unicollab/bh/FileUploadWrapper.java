package it.unicollab.bh;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadWrapper {

    private MultipartFile image;

    private MultipartFile background;


    private MultipartFile curriculum;


    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getBackground() {
        return background;
    }

    public void setBackground(MultipartFile background) {
        this.background = background;
    }

    public MultipartFile getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(MultipartFile curriculum) {
        this.curriculum = curriculum;
    }
}
