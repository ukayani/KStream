package kstream.repositories;

import kstream.domain.Episode;
import kstream.domain.Series;
import kstream.domain.Video;
import kstream.repositories.custom.EpisodeRepositoryCustom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-25
 * Time: 5:24 PM
 */
public interface EpisodeRepository extends CrudRepository<Episode, Long>, EpisodeRepositoryCustom {

    List<Episode> findByName(String name);
    List<Episode> findByLastUpdatedAfter(Date date);
    List<Episode> findBySeriesAndSeasonNumber(Series series, Integer seasonNumber);

    @Query("select e from Episode e left join fetch e.series s where e.series.id = ?1 and e.seasonNumber > 0 order by e.seasonNumber asc,e.episodeNumber asc")
    List<Episode> findBySeriesId(Long seriesId);

    @Query("select e from Episode e where ?1 in elements(e.videos)")
    Episode findByVideo(Video video);
}
