package kstream.controllers;

import kstream.dtos.UserRegistrationForm;
import kstream.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-02
 * Time: 2:16 PM
 */
@Controller
public class UserController {

    @Autowired
    private UserService _userService;

    @RequestMapping(value = "/login")
    public String loginPage(){
        return "auth/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(ModelMap model){

        model.addAttribute("user", new UserRegistrationForm());

        return "auth/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationForm user, BindingResult result){

        if (result.hasErrors()){
            return "auth/register";
        }

        if (_userService.usernameExists(user.getUsername())){
            result.rejectValue("username","registration.username.exists");
            return "auth/register";
        }

        _userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail(), "user");

        return "redirect:/login";
    }

    @RequestMapping(value = "/usernamecheck")
    public @ResponseBody Dictionary<String, Boolean> usernameCheck(@RequestParam("username") String username){

        Dictionary<String, Boolean> response = new Hashtable<String, Boolean>();
        response.put("success", false);

        if (_userService.usernameExists(username)){
           response.put("success", true);
        }

        return response;
    }

    @RequestMapping(value = "/denied")
    public String accessDenied(){
        return "auth/denied";
    }

}
