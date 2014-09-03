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
        dest.setTvDbId(series.getId());
        dest.setName(series.getSeriesName());
        series.getGenres().stream().map(g -> getGenreByName(g)).forEach(g -> dest.addGenre(g));
        dest.setAirDay(series.getAirsDayOfWeek());
        dest.setAirTime(series.getAirsTime());
        dest.setBanner(series.getBanner());
        dest.setPriority(ConversionPriority.Low);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date firstAirDate = null;
        try {
            firstAirDate = formatter.parse(series.getFirstAired());
        }
        catch (Exception e){

        }

        dest.setFirstAired(firstAirDate);
        dest.setContentRating(series.getContentRating());
        dest.setImdbId(series.getImdbId());
        dest.setNetwork(series.getNetwork());
        dest.setOverview(series.getOverview());
        dest.setRating(Double.parseDouble(series.getRating()));
        dest.setRuntime(series.getRuntime());
        dest.setStatus(series.getStatus());

        return dest;
    }

    public Series get(kstream.domain.Series series){
        throw new NotImplementedException();
    }

    private Genre getGenreByName(String name){

        Genre g = _genreRepository.findByName(name);

        if (g == null) g = new Genre(name);

        return g;
    }
}
