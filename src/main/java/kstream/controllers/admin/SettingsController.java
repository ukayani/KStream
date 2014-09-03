package kstream.controllers.admin;

import kstream.domain.AppSettings;
import kstream.domain.ConversionMode;
import kstream.domain.ConversionProfile;
import kstream.dtos.SettingsForm;
import kstream.services.SettingsService;
import kstream.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-09
 * Time: 3:08 PM
 */

@Controller
public class SettingsController {

    @Autowired
    private SettingsService _settingsService;

    @Autowired
    private VideoService _conversionService;


    @ModelAttribute("profiles")
    public Iterable<ConversionProfile> getConversionProfiles(){
        System.out.println("Getting profiles");
        return _conversionService.getConversionProfiles();
    }

    @ModelAttribute("modes")
    public Iterable<ConversionMode> getConversionModes(){
        return ConversionMode.MODE_LIST;
    }

    @RequestMapping(value = "/admin/settings", method = RequestMethod.GET)
    public String getSettings(ModelMap map){

        AppSettings settings = _settingsService.getSettings();
        SettingsForm form = new SettingsForm();

        if (settings != null){
            form.setConversionMode(settings.getConversionMode());
            form.setProfileId(settings.getDefaultProfile().getId());
        }

        map.addAttribute("settings", form);

        return "admin/settings";
    }

    @RequestMapping(value = "/admin/settings", method = RequestMethod.POST)
    public String getSettingsPost(@ModelAttribute("settings") SettingsForm form, BindingResult result){

        if (result.hasErrors()){
            return "admin/settings";
        }

        _settingsService.updateSettings(form);

        return "redirect:/admin";
    }



}
