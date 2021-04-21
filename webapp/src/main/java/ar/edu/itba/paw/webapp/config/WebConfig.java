package ar.edu.itba.paw.webapp.config;

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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Properties;
import java.nio.charset.StandardCharsets;

@EnableWebMvc
@ComponentScan({"ar.edu.itba.paw.webapp.controller", "ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence",
        "ar.edu.itba.paw.webapp.validation", "ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableAsync
public class WebConfig {

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();

        ds.setDriverClass(org.postgresql.Driver.class);

//        PARA USO LOCAL
        ds.setUrl("jdbc:postgresql://localhost:5432/paw-2021a-03");

//        PARA DEPLOY
//        ds.setUrl("jdbc:postgresql://10.16.1.110:5432/paw-2021a-03");

        ds.setUsername("paw-2021a-03");
        ds.setPassword("4Jqbf4tiN");

        return ds;
    }

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Value("classpath:migration_image_schema.sql")
    private Resource imageSchemaSql;

    @Value("classpath:migration_login.sql")
    private Resource loginMigration;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());
        return dsi;
    }
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScript(schemaSql);
        dbp.addScript(imageSchemaSql);
        dbp.addScript(loginMigration);
        return dbp;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        long MAX_FILE_SIZE = 5242880;       //5MB
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_FILE_SIZE);
        return multipartResolver;
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
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("paw.hirenet@gmail.com");
        mailSender.setPassword("paw12345");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Value("classpath:contractEmail.html")
    Resource contractTemplate;
    @Value("classpath:contractEmailWithImage.html")
    Resource contractImageTemplate;
    @Value("classpath:tokenEmail.html")
    Resource tokenEmailTemplate;

    private SimpleMailMessage makeMessage(Resource template) {
        SimpleMailMessage message = new SimpleMailMessage();
        String text="";
        try{
            Reader reader = new InputStreamReader(template.getInputStream());
            text = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        message.setText(text);
        return message;
    }

    @Bean(name = "contractEmail")
    public SimpleMailMessage contractEmail() {
        return makeMessage(contractTemplate);
    }

    @Bean(name = "contractEmailWithImage")
    public SimpleMailMessage contractEmailWithImage() {
        return makeMessage(contractImageTemplate);
    }

    @Bean(name = "tokenEmail")
    public SimpleMailMessage tokenEmail() {
        return makeMessage(tokenEmailTemplate);
    }

}
