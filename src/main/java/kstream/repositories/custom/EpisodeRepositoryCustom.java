package kstream.repositories.custom;

import kstream.domain.Episode;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-25
 * Time: 8:31 PM
 */
public interface EpisodeRepositoryCustom {

    Episode getEpisodeWithVideo(Long episodeId);
}
