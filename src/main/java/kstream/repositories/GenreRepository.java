package kstream.repositories;

import kstream.domain.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-25
 * Time: 5:02 PM
 */
public interface GenreRepository extends CrudRepository<Genre, Long> {

     List<Genre> findByNameContaining(String name);
     Genre findByName(String name);
}
