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
        update(episode, ep);
        return ep;
    }

    public Episode get(kstream.domain.Episode episode){
        throw new NotImplementedException();
    }

    @Override
    public void update(Episode source, kstream.domain.Episode toUpdate) {
        toUpdate.setName(source.getEpisodeName());
        toUpdate.setEpisodeNumber(source.getEpisodeNumber());
        toUpdate.setOverview(source.getOverview());
        toUpdate.setTotalVideoCount(0);
        toUpdate.setVisibleVideoCount(0);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date firstAirDate = null;
        try {
            firstAirDate = formatter.parse(source.getFirstAired());
        }
        catch (Exception e){

        }
        toUpdate.setFirstAired(firstAirDate);

        toUpdate.setSeasonNumber(source.getSeasonNumber());
        toUpdate.setTvDbId(source.getId());

    }


}
