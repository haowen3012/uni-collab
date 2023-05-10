package it.unicollab.bh.repository;

import org.springframework.data.repository.CrudRepository;

import it.unicollab.bh.model.Credentials;
import it.unicollab.bh.model.Picture;


public interface PictureRepository extends CrudRepository<Picture, Long> {

}
