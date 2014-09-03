package kstream.controllers.admin;

import kstream.domain.Codec;
import kstream.domain.MediaContainer;
import kstream.dtos.ProfileForm;
import kstream.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-26
 * Time: 3:57 PM
 */

@Controller
public class ConversionController {

    @Autowired
    private VideoService _conversionService;


    @ModelAttribute("codecs")
    public List<Codec> getCodecs(){
        return Codec.CODEC_LIST;
    }

    @ModelAttribute("containers")
    public List<MediaContainer> getContainers(){
        return MediaContainer.CONTAINERS_LIST;
    }

    @RequestMapping(value = "/admin/conversion/profile/add", method = RequestMethod.GET)
    public String addConversionProfile(ModelMap modelMap){

        modelMap.addAttribute("profile", new ProfileForm());
        return "admin/conversion/addprofile";
    }

    @RequestMapping(value = "/admin/conversion/profile/add", method = RequestMethod.POST)
    public String addConversionProfilePost(@Valid @ModelAttribute("profile") ProfileForm profileForm, BindingResult result){

        if (result.hasErrors()) return "/admin/conversion/addprofile";

        _conversionService.addConversionProfile(profileForm);

        return "redirect:/admin";

    }


}
