package kstream.config;

import kstream.domain.MediaType;
import kstream.notifiers.VideoConversionNotifier;
import kstream.services.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-20
 * Time: 3:48 PM
 */

@Configuration
public class EventConfig {

    @Autowired
    private TVService _tvService;

    @Bean
    public VideoConversionNotifier getNotifier(){
        VideoConversionNotifier notifier = new VideoConversionNotifier();
        notifier.subscribe(MediaType.TV, _tvService);

        return notifier;
    }
}
