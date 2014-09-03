package kstream.mappers;

import com.omertron.thetvdbapi.model.Series;
import kstream.Application;
import kstream.domain.ConversionPriority;
import kstream.domain.Genre;
import kstream.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-06
 * Time: 7:53 PM
 */
@Component
public class SeriesMapper implements Mapper<Series, kstream.domain.Series> {

    @Autowired
    private GenreRepository _genreRepository;

    public kstream.domain.Series map(Series series){

        kstream.domain.Series dest = new kstream.domain.Series();
        update(series, dest);
        return dest;
    }

    public Series get(kstream.domain.Series series){
        throw new NotImplementedException();
    }

    @Override
    public void update(Series source, kstream.domain.Series toUpdate) {

        toUpdate.setTvDbId(source.getId());
        toUpdate.setName(source.getSeriesName());

        // don't update genres if it already had them
        if (toUpdate.getGenres() == null || toUpdate.getGenres().size() == 0){
            source.getGenres().stream().map(g -> getGenreByName(g)).forEach(g -> toUpdate.addGenre(g));
        }

        toUpdate.setAirDay(source.getAirsDayOfWeek());
        toUpdate.setAirTime(source.getAirsTime());
        toUpdate.setBanner(source.getBanner());
        toUpdate.setPriority(ConversionPriority.Low);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date firstAirDate = null;
        try {
            firstAirDate = formatter.parse(source.getFirstAired());
        }
        catch (Exception e){

        }

        toUpdate.setFirstAired(firstAirDate);
        toUpdate.setContentRating(source.getContentRating());
        toUpdate.setImdbId(source.getImdbId());
        toUpdate.setNetwork(source.getNetwork());
        toUpdate.setOverview(source.getOverview());
        toUpdate.setRating(Double.parseDouble(source.getRating()));
        toUpdate.setRuntime(source.getRuntime());
        toUpdate.setStatus(source.getStatus());
        toUpdate.setLastUpdated(Integer.parseInt(source.getLastUpdated()));

    }


    private Genre getGenreByName(String name){

        Genre g = _genreRepository.findByName(name);

        if (g == null) g = new Genre(name);

        return g;
    }
}
