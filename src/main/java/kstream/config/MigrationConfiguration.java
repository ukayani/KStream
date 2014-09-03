package kstream.config;

import com.googlecode.flyway.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-03
 * Time: 8:24 PM
 */

@Configuration
@AutoConfigureAfter(DatabaseConfiguration.class)
public class MigrationConfiguration {

    @Autowired
    private DataSource _datasource;

    @Bean
    public Flyway flyway(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(_datasource);

        flyway.migrate();
        return flyway;
    }
}
