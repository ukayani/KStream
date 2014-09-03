package kstream.managers.filesystem;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-03
 * Time: 11:44 PM
 */

@Component
public class FileSystemManager {

    public List<FileItem> getDirectoryListing(String directory){

        List<FileItem> files = new ArrayList<FileItem>();

        File d = new File(directory);

        if (!d.isDirectory()) return files;

        FileItemFilter filter = new FileItemFilter("\\.(avi|mp4|mkv|webm|mpeg|mpg)$");

        files.addAll(Arrays.asList(d.listFiles(filter)).stream().map(f -> new FileItem(f)).collect(Collectors.toList()));

        return files;

    }
}
