package kstream.controllers.admin;

import kstream.managers.filesystem.FileItem;
import kstream.services.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-03
 * Time: 11:26 PM
 */

@Controller
public class FileSystemController {

    @Autowired
    private FileSystemService _fs;

    @Autowired
    private Environment _env;

    private static final String KEY_ALLOWED_BASE = "kstream.media.browser.base";


    @RequestMapping(value = "/admin/fs/ls", method = RequestMethod.POST)
    public @ResponseBody List<FileItem> getDirectoryListing(@RequestParam(value = "path", required = false) String path){


        String basePath = _env.getProperty(KEY_ALLOWED_BASE);

        if (path == null || path == ""){
            path = basePath;
        }

        return _fs.getDirectoryListing(path, basePath);
    }

}
