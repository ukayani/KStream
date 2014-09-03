package kstream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-07-01
 * Time: 6:20 PM
 */

@Configuration
public class WebHandlerConfig extends WebMvcConfigurationSupport{

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        adapter.setIgnoreDefaultModelOnRedirect(true);
        return adapter;
    }

}
