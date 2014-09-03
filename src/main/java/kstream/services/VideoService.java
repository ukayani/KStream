package kstream.services;

import kstream.domain.*;
import kstream.factories.VideoFactory;
import kstream.dtos.ProfileForm;
import kstream.processors.IVideoProcessor;
import kstream.repositories.ConversionItemRepository;
import kstream.repositories.ConversionProfileRepository;
import kstream.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-06-25
 * Time: 11:06 AM
 */

@Service
public class VideoService {


    @Autowired
    private VideoRepository _videoRepository;

    @Autowired
    private ConversionItemRepository _conversionItemRepository;

    @Autowired
    private ConversionProfileRepository _conversionProfileRepository;

    @Autowired
    private IVideoProcessor _processor;

    @Autowired
    private VideoFactory _videoFactory;


    @Transactional
    public Boolean createConversionItem(Long sourceId, Long destinationId, ConversionPriority priority,
                                        Long profileId, MediaType mediaType){

        Video source = _videoRepository.findOne(sourceId);
        Video dest = _videoRepository.findOne(destinationId);

        ConversionItem item = new ConversionItem();
        item.setDateCreated(new Date());
        item.setSource(source.getUrl());
        item.setDestination(dest.getUrl());
        item.setStatus(ConversionStatus.Idle);
        item.setPriority(priority);
        item.setVideo(dest);
        item.setMediaType(mediaType);

        ConversionProfile profile = _conversionProfileRepository.findOne(profileId);
        item.setProfile(profile);
        dest.setProfile(profile);

        _conversionItemRepository.save(item);
        _videoRepository.save(dest);

        return true;
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 60000) //3600000 = hour
    public void CheckHighPriorityConversions(){

        Iterable<ConversionItem> items = _conversionItemRepository.getAllItemsWithPriorityAndStatus(ConversionPriority.High,
                ConversionStatus.Idle);

        processConversionItems(items);

    }

    private void processConversionItems(Iterable<ConversionItem> items){

        for (ConversionItem item: items){

            setConversionItemStatus(item.getId(), ConversionStatus.Queued);

            try {
                _processor.process(item.getId(), this);
            }
            catch (RejectedExecutionException e){
                System.out.println("rejected execution");
                setConversionItemStatus(item.getId(), ConversionStatus.Idle);
                break;
            }
        }
    }

    @Transactional
    public void updateVideoFromUrl(Long videoId){
        Video video = _videoRepository.findOne(videoId);
        video = _videoFactory.getUpdatedVideo(video);
        _videoRepository.save(video);
    }

    @Transactional(readOnly = true)
    public Video getVideoById(Long id){
        return _videoRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public Iterable<ConversionProfile> getConversionProfiles(){

        return _conversionProfileRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ConversionProfile getConversionProfile(Long id){
        return _conversionProfileRepository.findOne(id);
    }

    @Transactional
    public Boolean addConversionProfile(ProfileForm profileDto){

        ConversionProfile profile = new ConversionProfile();
        profile.setFileSuffix(profileDto.getFileSuffix());
        profile.setLabel(profileDto.getLabel());
        profile.setAudioCodec(profileDto.getAudioCodec());
        profile.setAudioBitrate(profileDto.getAudioBitrate());
        profile.setAudioOptions(profileDto.getAudioOptions());
        profile.setVideoCodec(profileDto.getVideoCodec());
        profile.setVideoBitrate(profileDto.getVideoBitrate());
        profile.setVideoOptions(profileDto.getVideoOptions());
        profile.setFormat(profileDto.getContainer());

        _conversionProfileRepository.save(profile);
        return true;

    }

    @Transactional
    public void setConversionItemStatus(Long id, ConversionStatus status){
        ConversionItem item = _conversionItemRepository.findOne(id);
        item.setStatus(status);
        _conversionItemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public ConversionItem getConversionItem(Long id){
        return _conversionItemRepository.findOne(id);
    }

}
