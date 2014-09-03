package kstream.managers.filesystem;

import com.google.common.io.Files;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-03
 * Time: 11:45 PM
 */
public class FileItem {

    private Boolean isDirectory;

    private String path;

    private String extension;

    private String filename;

    // zero arg constructor
    public FileItem(){

    }

    public FileItem(File file){

        isDirectory = file.isDirectory();
        path = file.getPath();
        extension = Files.getFileExtension(path);
        filename = file.getName();
    }

    public Boolean getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
