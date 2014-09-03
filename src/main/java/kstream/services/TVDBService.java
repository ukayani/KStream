package kstream.services;

import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.model.Episode;
import com.omertron.thetvdbapi.model.Series;
import kstream.mappers.Mapper;
import kstream.mappers.SeriesMapper;
import kstream.repositories.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-06
 * Time: 7:33 PM
 */

@Service
public class TVDBService {

    @Autowired
    private TheTVDBApi _tvDbApi;

    public List<Series> searchSeriesByName(String name, String language){
        return _tvDbApi.searchSeries(name, language);
    }

    public List<Series> searchSeriesByName(String name){
        return searchSeriesByName(name, "en");
    }

    public Series getSeriesById(String id, String language){
        return _tvDbApi.getSeries(id, language);
    }

    public Series getSeriesById(String id){
        return getSeriesById(id, "en");
    }

    public List<Episode> getAllEpisodesBySeriesId(String id, String language){
        return _tvDbApi.getAllEpisodes(id, language);
    }

    public List<Episode> getAllEpisodesBySeriesId(String id){
        return getAllEpisodesBySeriesId(id, "en");
    }

}
