package kstream.processors;

import kstream.domain.ConversionItem;
import kstream.domain.ConversionStatus;
import kstream.domain.Video;
import kstream.notifiers.events.VideoConvertedEvent;
import kstream.mappers.Mapper;
import kstream.processors.converter.FFMpegArgs;
import kstream.processors.converter.IVideoConverter;
import kstream.processors.converter.VideoProfile;
import kstream.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-27
 * Time: 2:54 PM
 */

@Component
public class VideoProcessor implements IVideoProcessor, ApplicationEventPublisherAware {

    @Autowired
    private IVideoConverter _converter;

    private ApplicationEventPublisher publisher;

    @Autowired
    private Mapper<Video, VideoProfile> _videoToProfileMapper;

    @Async
    public Future<Boolean> process(Long conversionItemId, VideoService service){

        StopWatch watch = new StopWatch();

        ConversionItem item = service.getConversionItem(conversionItemId);

        Video video = item.getVideo();
        VideoProfile source = _videoToProfileMapper.map(video);

        service.setConversionItemStatus(conversionItemId, ConversionStatus.InProgress);

        watch.start();

        FFMpegArgs args = new FFMpegArgs(source, item.getProfile(), item.getSource(), item.getDestination());

        Boolean success = _converter.convert(args);

        watch.stop();
        System.out.println("Conversion Duration: " + watch.getTotalTimeSeconds());

        ConversionStatus status = (success)? ConversionStatus.Completed: ConversionStatus.Failed;

        service.setConversionItemStatus(conversionItemId, status);

        if (success){
            service.updateVideoFromUrl(item.getVideo().getId());

            VideoConvertedEvent event = new VideoConvertedEvent(video, video.getId(), item.getMediaType());
            publisher.publishEvent(event);
        }

        return new AsyncResult<Boolean>(true);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
