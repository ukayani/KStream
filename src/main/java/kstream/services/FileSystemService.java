package kstream.services;

import kstream.managers.filesystem.FileItem;
import kstream.managers.filesystem.FileSystemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-03
 * Time: 11:05 PM
 */

@Component
public class FileSystemService {

    @Autowired
    private FileSystemManager _fsm;


    public List<FileItem> getDirectoryListing(String path, String allowedBase){

        Pattern p1 = Pattern.compile(String.format("^%s", allowedBase));
        if (!p1.matcher(path).find()) return new ArrayList<FileItem>();

        return _fsm.getDirectoryListing(path);
    }

}
