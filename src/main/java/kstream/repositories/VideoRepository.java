package kstream.repositories;

import kstream.domain.Video;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-25
 * Time: 8:01 PM
 */
public interface VideoRepository extends CrudRepository<Video, Long> {
}
