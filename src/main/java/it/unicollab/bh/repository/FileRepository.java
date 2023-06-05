package it.unicollab.bh.repository;

import it.unicollab.bh.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {

     File findByBytes(byte[] bytes);

}
