package kstream.controllers;

import kstream.domain.Episode;
import kstream.domain.Series;
import kstream.domain.Video;
import kstream.processors.SeriesFolderProcessor;
import kstream.services.TVService;
import kstream.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-10
 * Time: 6:45 PM
 */

@Controller
public class TVController {

    @Autowired
    private TVService _tvService;

    @Autowired
    private VideoService _videoService;

    @RequestMapping("/tv/series/find")
    public @ResponseBody List<Series> searchSeries(@RequestParam("key") String key){

        return _tvService.searchSeriesByName(key);
    }

    @RequestMapping("/tv/series/{seriesId}/season/{season}/episodes")
    public @ResponseBody List<Episode> getEpisodesBySeason(@PathVariable Long seriesId, @PathVariable Integer season){

        return _tvService.getEpisodesForSeriesBySeasonNumber(seriesId, season);

    }

    @RequestMapping("/tv/series/{seriesId}/episodes")
    public String getEpisodesBySeries(@PathVariable Long seriesId, ModelMap model){

        _tvService.updateSeriesById(seriesId, "en");

        List<List<Episode>> seasons =  _tvService.getSeriesEpisodes(seriesId);

        model.addAttribute("seasons", seasons);
        Series s = _tvService.getSeriesById(seriesId);
        model.addAttribute("series", s);

        return "series/episodes";
    }


    @RequestMapping("/tv/series/all")
    public String getAllSeries(ModelMap model){

        model.addAttribute("seriesList", _tvService.getAllSeries());

        return "series/all";
    }

    @RequestMapping("/tv/watch/episode/{episodeId}")
    public String watchEpisodeVideo(@PathVariable Long episodeId, ModelMap modelMap){

        Episode episode = _tvService.getEpisodeWithVideos(episodeId);

        if (episode.getVisibleVideoCount() == 0) return "redirect:/";

        // get visible videos
        List<Video> videos = episode.getVideos().stream().filter(v -> v.isWebEnabled()).collect(Collectors.toList());

        Video video = videos.get(0);
        Path path = Paths.get(video.getUrl());
        modelMap.addAttribute("video", videos.get(0));
        modelMap.addAttribute("name", episode.getName());
        modelMap.addAttribute("videos", videos);
        modelMap.addAttribute("videoFileName", path.getFileName());

        return "video/watch";
    }

}
