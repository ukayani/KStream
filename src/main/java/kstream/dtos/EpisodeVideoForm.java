package kstream.dtos;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-23
 * Time: 5:30 PM
 */
public class EpisodeVideoForm {

    @NotEmpty
    private String videoPath;


    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
