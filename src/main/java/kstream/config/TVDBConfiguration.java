package kstream.config;

import com.omertron.thetvdbapi.TheTVDBApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-06
 * Time: 5:01 PM
 */

@Configuration
public class TVDBConfiguration {

    public static final String KEY_API = "tvdb.api.key";

    @Autowired
    private Environment env;

    @Bean
    public TheTVDBApi tvDbApi(){
        TheTVDBApi api = new TheTVDBApi(env.getProperty(KEY_API));
        return api;
    }

}
