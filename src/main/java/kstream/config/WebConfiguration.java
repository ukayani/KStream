package kstream.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-06
 * Time: 11:06 PM
 */

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    public static final String RESOURCE_PATH = "classpath:/static/";
    public static final String RESOURCE_PATTERN = "/r/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler(RESOURCE_PATTERN).addResourceLocations(RESOURCE_PATH);
    }

    @Bean
    public MessageSource messageSource(){

            ResourceBundleMessageSource source = new ResourceBundleMessageSource();
            source.setBasename("i18n/messages");
            source.setUseCodeAsDefaultMessage(true);
            return source;
    }


    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate4Module to support lazy objects
        mapper.registerModule(new Hibernate4Module());

        messageConverter.setObjectMapper(mapper);
        return messageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }

}
