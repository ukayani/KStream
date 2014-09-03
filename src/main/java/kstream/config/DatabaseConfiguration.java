package kstream.config;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 11:47 PM
 */

@Configuration
@EnableJpaRepositories("kstream.repositories")
@EnableTransactionManagement(proxyTargetClass = true)
public class DatabaseConfiguration {

    public static final String KEY_PACKAGES = "database.domain.packages";
    public static final String KEY_DIALECT = "database.orm.dialect";
    public static final String KEY_SHOWSQL = "database.showsql";

    @Autowired
    private Environment env;

    @Autowired
    private DataSource _datasource;


    @Bean
    public EntityManagerFactory entityManagerFactory(){

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(_datasource);

        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(env.getProperty(KEY_PACKAGES));
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty(KEY_DIALECT));
        //props.setProperty("hibernate.show_sql", env.getProperty(KEY_SHOWSQL));
        props.setProperty("hibernate.use_sql_comments", "true");
        props.setProperty("hibernate.format_sql", "true");
        factory.setJpaProperties(props);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
