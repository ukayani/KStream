package kstream.repositories;

import kstream.domain.Episode;
import kstream.domain.Series;
import kstream.repositories.custom.SeriesRepositoryCustom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-28
 * Time: 5:12 PM
 */
public interface SeriesRepository extends CrudRepository<Series, Long>, SeriesRepositoryCustom {

    List<Series> findByName(String name);
    List<Series> findByNameContaining(String name);
}
