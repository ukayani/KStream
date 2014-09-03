package kstream.managers.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-03
 * Time: 11:56 PM
 */
public class FileItemFilter implements FileFilter {

    public FileItemFilter(String regex){
        this(true, false, regex);
    }

    public FileItemFilter(Boolean showDirectories, Boolean showHidden, String regex){
        this.showDirectories = showDirectories;
        this.showHidden = showHidden;
        this.pattern = Pattern.compile(regex);
    }

    private Boolean showDirectories;
    private Boolean showHidden;
    private Pattern pattern;

    public boolean accept(File pathname) {

        FileItem item = new FileItem(pathname);

        if (this.showDirectories == false && pathname.isDirectory()) return false;
        if (this.showHidden == false && pathname.isHidden()) return false;
        // if we got here and we still see a directory, show it
        if (pathname.isDirectory()) return true;


        Matcher m = pattern.matcher(pathname.getPath());

        if (m.find()) return true;

        return false;
    }
}
