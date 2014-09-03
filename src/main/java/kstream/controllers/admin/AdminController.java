package kstream.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-23
 * Time: 4:05 PM
 */
@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String adminIndex(){
        return "admin/main";
    }
}
