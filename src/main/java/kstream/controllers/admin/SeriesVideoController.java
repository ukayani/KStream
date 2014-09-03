package kstream.controllers.admin;

import kstream.domain.*;
import kstream.dtos.ConversionCreationForm;
import kstream.dtos.JsonResponse;
import kstream.dtos.SeriesSettingsForm;
import kstream.factories.VideoFactory;
import kstream.managers.EpisodeVideoManager;
import kstream.managers.filesystem.FileItem;
import kstream.services.TVService;
import kstream.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-23
 * Time: 4:54 PM
 */

@Controller
public class SeriesVideoController {

    @Autowired
    private EpisodeVideoManager _evm;

    @Autowired
    private TVService _tvService;

    @Autowired
    private VideoService _conversionService;

    @Autowired
    private VideoFactory _vf;

    @RequestMapping(value = "/admin/tv/series/episode/{episodeId}/video/add", method = RequestMethod.GET)
    public String addEpisodeVideo(@PathVariable Long episodeId, ModelMap model){

        return "admin/series/episodes/addvideo";
    }

    @RequestMapping(value = "/admin/tv/series/episode/{episodeId}/video/add", method = RequestMethod.POST)
    public String addEpisodeVideoPost(@PathVariable Long episodeId, @RequestParam(value = "videoPath") String videoPath){

        if (videoPath.isEmpty()){
            return "admin/series/episodes/addvideo";
        }

        _tvService.addVideoToEpisode(episodeId, videoPath);

        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/tv/series/episode/{episodeId}/videos")
    public String getEpisodeVideos(@PathVariable Long episodeId, ModelMap model){

        Episode ep = _tvService.getEpisodeWithVideos(episodeId);

        if (ep == null) return "redirect:/admin";

        model.addAttribute("episode", ep);

        return "admin/series/episodes/videos";
    }

    @RequestMapping(value = "/admin/tv/episode/{episodeId}/video/{videoId}/delete")
    public String removeEpisodeVideo(@PathVariable Long episodeId, @PathVariable Long videoId){

        _tvService.removeVideo(episodeId, videoId);

        return "redirect:/admin";
    }


    @ModelAttribute("profiles")
    public Iterable<ConversionProfile> getConversionProfiles(){
        System.out.println("Getting profiles");
        return _conversionService.getConversionProfiles();
    }

    @ModelAttribute("priorities")
    public List<ConversionPriority> getConversionPriorities(){

        List<ConversionPriority> priorities = new ArrayList<ConversionPriority>();
        priorities.add(ConversionPriority.High);
        priorities.add(ConversionPriority.Low);

        return priorities;
    }

    @RequestMapping(value = "/admin/tv/episode/{episodeId}/video/{videoId}/convert", method = RequestMethod.GET)
    public String convertEpisodeVideo(@PathVariable Long episodeId, @PathVariable Long videoId, ModelMap modelMap){

        ConversionCreationForm creation = new ConversionCreationForm();
        creation.setPriority(ConversionPriority.Low);

        modelMap.addAttribute("conversionItem", creation);

        return "admin/series/episodes/convert";
    }

    @RequestMapping(value = "/admin/tv/episode/{episodeId}/video/{videoId}/convert", method = RequestMethod.POST)
    public String convertEpisodeVideoPost(@PathVariable Long episodeId, @PathVariable Long videoId, @Valid @ModelAttribute("conversionItem") ConversionCreationForm conversion, BindingResult result){

        if (result.hasErrors()){
            return "admin/series/episode/convert";
        }

        ConversionProfile profile = _conversionService.getConversionProfile(conversion.getProfileId());
        Video video = _tvService.addConversionVideoToEpisode(videoId, episodeId, profile.getFileSuffix(), profile.getFormat().name().toLowerCase());
        _conversionService.createConversionItem(videoId, video.getId(), conversion.getPriority(), conversion.getProfileId(), MediaType.TV);

        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/tv/series/{seriesId}/video/multiadd",
            method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public @ResponseBody JsonResponse addSeriesVideos(@PathVariable Long seriesId, @RequestBody FileItem[] files){

        if (files.length == 0) return new JsonResponse("FAIL", "");

        List<FileItem> pathList = Arrays.asList(files);

        _tvService.addVideosToSeries(seriesId, pathList.stream().map(f -> f.getPath()).collect(Collectors.toList()));

        return new JsonResponse("OK", "");
    }

    @RequestMapping(value = "/admin/tv/series/{seriesId}/settings", method = RequestMethod.GET)
    public String seriesSettings(@PathVariable Long seriesId, ModelMap map){

        Series s = _tvService.getSeriesById(seriesId);
        SeriesSettingsForm settings = new SeriesSettingsForm();
        settings.setPriority(s.getPriority());
        if (s.getDefaultProfile() != null){
            settings.setProfileId(s.getDefaultProfile().getId());
        }
        map.addAttribute("settings", settings);

        return "admin/series/settings";
    }

    @RequestMapping(value = "/admin/tv/series/{seriesId}/settings", method = RequestMethod.POST)
    public String seriesSettingsPost(@PathVariable Long seriesId, @ModelAttribute("settings") SeriesSettingsForm settings, BindingResult result){

        if (!_tvService.updateSeriesSettings(seriesId, settings) || result.hasErrors()){
            return "admin/series/settings";
        }

        return "redirect:/admin";
    }

}
