package kstream.mappers;

import com.google.common.io.Files;
import kstream.domain.ConversionProfile;
import kstream.domain.MediaContainer;
import kstream.domain.Video;
import kstream.processors.converter.VideoProfile;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-30
 * Time: 8:44 PM
 */
@Component
public class VideoToProfileMapper implements Mapper<Video, VideoProfile> {


    @Override
    public Video get(VideoProfile source) {
        throw new NotImplementedException();
    }

    @Override
    public VideoProfile map(Video source) {

        VideoProfile profile = new VideoProfile();

        profile.setAudioCodec(source.getAudioCodec());
        profile.setVideoCodec(source.getVideoCodec());
        profile.setAudioBitrate(source.getAudioBitrate());
        profile.setVideoBitrate(source.getVideoBitrate());
        profile.setWidth(source.getWidth());
        profile.setHeight(source.getHeight());
        profile.setSize(source.getSize());
        profile.setDuration(source.getDuration());

        return profile;

    }

    private MediaContainer getMediaContainerFromExtension(String extension){

        Map<String, MediaContainer> map = MediaContainer.CONTAINERS_LIST.stream()
                .collect(Collectors.toMap(m -> m.name().toLowerCase(), m -> m));

        if (map.containsKey(extension.toLowerCase())){
            return map.get(extension.toLowerCase());
        }

        return MediaContainer.UNKNOWN;
    }
}
