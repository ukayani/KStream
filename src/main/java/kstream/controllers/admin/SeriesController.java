package kstream.controllers.admin;

import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.model.Series;
import kstream.domain.Episode;
import kstream.services.TVDBService;
import kstream.services.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-06
 * Time: 5:52 PM
 */
@Controller
public class SeriesController {

    @Autowired
    private TVDBService _tvDbService;

    @Autowired
    private TVService _tvService;

    @RequestMapping("/admin/tv/search")
    public String tvSearch(ModelMap modelMap, @RequestParam(value = "key", required = false)String key){

        if (key != null){
            modelMap.addAttribute("series", _tvDbService.searchSeriesByName(key));
        }
        return "admin/series/search";
    }

    @RequestMapping("/admin/tv/add/{seriesId}")
    public String tvAdd(@PathVariable String seriesId, RedirectAttributes redirectAttributes){

        _tvService.addSeriesByTvDbId(seriesId, "en");

        redirectAttributes.addFlashAttribute("message", "Successfully added Series.");
        return "redirect:/admin";
    }

    @RequestMapping("/admin/tv/series/all")
    public String getAllSeries(ModelMap model){

        model.addAttribute("seriesList", _tvService.getAllSeries());

        return "admin/series/all";
    }

    @RequestMapping("/admin/tv/series/{seriesId}/episodes")
    public String getEpisodesBySeries(@PathVariable Long seriesId, ModelMap model){

        List<List<Episode>> seasons =  _tvService.getSeriesEpisodes(seriesId);

        model.addAttribute("seasons", seasons);
        kstream.domain.Series s = _tvService.getSeriesById(seriesId);
        model.addAttribute("series", s);

        return "admin/series/episodes";
    }


    @RequestMapping("/admin/tv/series/{seriesId}/delete")
    public String deleteSeries(@PathVariable Long seriesId){
        _tvService.deleteSeriesById(seriesId);
        return "redirect:/admin";
    }

}
