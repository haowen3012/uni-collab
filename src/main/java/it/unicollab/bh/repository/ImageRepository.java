package it.unicollab.bh.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.unicollab.bh.model.Image;


public interface ImageRepository extends CrudRepository<Image, Long> {

	public Optional<Image> findById(String id);

}
