package ar.edu.itba.paw.webapp.config;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@ComponentScan({"ar.edu.itba.paw.webapp.restcontrollers", "ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence",
        "ar.edu.itba.paw.webapp.validation", "ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableTransactionManagement
@EnableAsync
public class WebConfig {

    // FIXME Cambiar para produccion
    private final boolean PRODUCTION = false;

    private final Logger webConfigLogger = LoggerFactory.getLogger(WebConfig.class);

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();

        ds.setDriverClass(org.postgresql.Driver.class);
        webConfigLogger.debug("Datasoruce driver set to {}", ds.getDriver());

        if(PRODUCTION) {
            //        PARA DEPLOY
            ds.setUrl("jdbc:postgresql://10.16.1.110:5432/paw-2021a-03");
        } else {
            //        PARA USO LOCAL
            ds.setUrl("jdbc:postgresql://localhost:5432/paw-2021a-03");
        }

        webConfigLogger.debug("Datasource URL set to {}", ds.getUrl());

        ds.setUsername("paw-2021a-03");
        ds.setPassword("4Jqbf4tiN");
        webConfigLogger.debug("Datasource username and password set to {} and {}", ds.getUsername(), ds.getPassword());

        return ds;
    }

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Value("classpath:migration_image_schema.sql")
    private Resource imageSchemaSql;

    @Value("classpath:migration_login.sql")
    private Resource loginMigration;

    @Value("classpath:job_card_view.sql")
    private Resource jobCardView;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());
        return dsi;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        webConfigLogger.debug("Running SQL script: {}", schemaSql.getFilename());
        dbp.addScript(schemaSql);
        webConfigLogger.debug("Running SQL script: {}", imageSchemaSql.getFilename());
        dbp.addScript(imageSchemaSql);
        webConfigLogger.debug("Running SQL script: {}", loginMigration.getFilename());
        dbp.addScript(loginMigration);
        webConfigLogger.debug("Running SQL script: {}", jobCardView.getFilename());
        dbp.addScript(jobCardView);
        return dbp;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory enf) {
        return new JpaTransactionManager(enf);
    }

    @Bean
    public LevenshteinDistance levenshteinDistance() {
        return new LevenshteinDistance();       //Se le puede indicar un threshold en el constructor
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        final LocalContainerEntityManagerFactoryBean entityFactory = new LocalContainerEntityManagerFactoryBean();
        entityFactory.setPackagesToScan("ar.edu.itba.paw.models");
        entityFactory.setDataSource(dataSource());

        final JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityFactory.setJpaVendorAdapter(jpaVendorAdapter);

        final Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto","update");
        jpaProperties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQL92Dialect");

        if(!PRODUCTION){
            //  Esto no va en produccion
            jpaProperties.setProperty("hibernate.show_sql", "true");
            jpaProperties.setProperty("format_sql", "true");
        }

        entityFactory.setJpaProperties(jpaProperties);

        return entityFactory;
    }
}
