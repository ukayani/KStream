package kstream.notifiers;

import kstream.domain.MediaType;
import kstream.notifiers.events.VideoConvertedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-20
 * Time: 2:34 PM
 */


public class VideoConversionNotifier implements ApplicationListener<VideoConvertedEvent> {

    private Map<MediaType, List<VideoConversionHandler>> handlersMap = new HashMap<MediaType, List<VideoConversionHandler>>();

    @Override
    public void onApplicationEvent(VideoConvertedEvent event) {

        if (!handlersMap.containsKey(event.getMediaType())) return;

        // notify all handlers for the specific media type
        List<VideoConversionHandler> handlers = handlersMap.get(event.getMediaType());

        for (VideoConversionHandler handler: handlers){
            handler.onVideoConversionComplete(event.getVideoId());
        }
    }

    public void subscribe(MediaType type, VideoConversionHandler handler){
        if (!handlersMap.containsKey(type)){
            handlersMap.put(type, new ArrayList<VideoConversionHandler>());
        }

        List<VideoConversionHandler> handlers = handlersMap.get(type);
        handlers.add(handler);
    }
}
