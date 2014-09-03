package kstream.notifiers.events;

import kstream.domain.MediaType;
import org.springframework.context.ApplicationEvent;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-20
 * Time: 1:55 PM
 */
public class VideoConvertedEvent extends ApplicationEvent {

    private Long videoId;

    private MediaType mediaType;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public VideoConvertedEvent(Object source, Long videoId, MediaType mediaType) {
        super(source);
        this.videoId = videoId;
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

}
