package kstream.mappers;

import com.omertron.thetvdbapi.model.Episode;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-10
 * Time: 12:18 AM
 */
@Component
public class EpisodeMapper implements Mapper<Episode, kstream.domain.Episode> {

    public kstream.domain.Episode map(Episode episode){
        kstream.domain.Episode ep = new kstream.domain.Episode();
        ep.setName(episode.getEpisodeName());
        ep.setEpisodeNumber(episode.getEpisodeNumber());
        ep.setOverview(episode.getOverview());
        ep.setTotalVideoCount(0);
        ep.setVisibleVideoCount(0);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date firstAirDate = null;
        try {
            firstAirDate = formatter.parse(episode.getFirstAired());
        }
        catch (Exception e){

        }
        ep.setFirstAired(firstAirDate);

        ep.setSeasonNumber(episode.getSeasonNumber());
        ep.setTvDbId(episode.getId());

        return ep;
    }

    public Episode get(kstream.domain.Episode episode){
        throw new NotImplementedException();
    }
}
