package kstream.controllers;

import com.omertron.thetvdbapi.TheTVDBApi;
import com.omertron.thetvdbapi.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-01
 * Time: 4:34 PM
 */

@Controller
public class MainController {


    @RequestMapping("/")
    public String mainPage(ModelMap model){
        model.addAttribute("message", "hello world");
        return "main";
    }

}
