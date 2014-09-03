package kstream.managers;

import kstream.domain.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-23
 * Time: 8:19 PM
 */
@Component
public class EpisodeVideoManager {

    @Autowired
    private Environment env;

    private static final String KEY_MEDIA_BASEPATH = "kstream.media.base";
    private static final String VIDEO_EXTENSION = "mp4";
    private static final String TEMP_SUFFIX = "Temp";

    public static String getEpisodeFileName(Episode episode, String suffix, String extension){

        String identifier = String.format("%02dx%02d", episode.getSeasonNumber(), episode.getEpisodeNumber());
        String filename = String.format("%s-%s-%s%s.%s", episode.getSeries().getName().trim(),
                identifier, episode.getName().trim(), suffix, extension);
        filename = filename.replaceAll("\\s+", ".").replaceAll("(\\\\|/)", "");

        return filename;
    }

    public String getTempEpisodePath(Episode episode){

        String suffix = TEMP_SUFFIX;

        File tempFile = new File(getEpisodePath(episode, suffix, VIDEO_EXTENSION));

        while (tempFile.exists()){
            suffix = suffix + "(1)";
            tempFile = new File(getEpisodePath(episode, suffix, VIDEO_EXTENSION));
        }

        return tempFile.getPath();
    }

    public String getNonExistingEpisodePath(Episode episode, String suffix, String fileExtension){

        String fsuffix = suffix;
        File tempFile = new File(getEpisodePath(episode, fsuffix, fileExtension));

        while (tempFile.exists()){
            fsuffix = "(1)" + fsuffix;
            tempFile = new File(getEpisodePath(episode, fsuffix, fileExtension));
        }

        return tempFile.getPath();
    }

    public String getEpisodePath(Episode episode, String suffix, String extension){

        String basepath = this.env.getProperty(KEY_MEDIA_BASEPATH);

        String seriesName = episode.getSeries().getName();
        String seasonName = String.format("Season %02d", episode.getSeasonNumber());
        String path = String.format("%s%s/%s/%s/%s", basepath, "TV", seriesName, seasonName, getEpisodeFileName(episode, suffix, extension));
        return path;
    }

    public String getEpisodePath(Episode episode, String extension){
        return getEpisodePath(episode, "", extension);
    }

    public String getEpisodePath(Episode episode){
        return getEpisodePath(episode, "", VIDEO_EXTENSION);
    }
}
