package ar.edu.itba.paw.persistence.test.config;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@ComponentScan({"ar.edu.itba.paw.persistence"})
@Configuration
@EnableTransactionManagement
public class TestConfig {

    @Value("classpath:hsqldb.sql")
    private Resource hsqldbSql;

    @Value("classpath:schema_test.sql")
    private Resource schemaSql;

    @Value("classpath:migration_image_schema_test.sql")
    private Resource imageSchema;

    @Value("classpath:migration_login_test.sql")
    private Resource loginMigration;

    @Value("classpath:job_card_view_test.sql")
    private Resource jobCardView;

    @Bean
    public DataSource dataSource() {
//        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        final SingleConnectionDataSource ds = new SingleConnectionDataSource();
//        ds.setDriverClass(JDBCDriver.class);
        ds.setDriverClassName("org.hsqldb.jdbcDriver");
        ds.setSuppressClose(true);
        ds.setUrl("jdbc:hsqldb:mem:paw-2021a-03");
        ds.setUsername("ha");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());
        return dsi;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScript(hsqldbSql);
        dbp.addScript(schemaSql);
        dbp.addScript(imageSchema);
        dbp.addScript(loginMigration);
        dbp.addScript(jobCardView);
        return dbp;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory enf) {
        return new JpaTransactionManager(enf);
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
        jpaProperties.setProperty("hibernate.dialect","org.hibernate.dialect.HSQLDialect");

        jpaProperties.setProperty("hibernate.show_sql", "true");
        jpaProperties.setProperty("format_sql", "true");

        entityFactory.setJpaProperties(jpaProperties);

        return entityFactory;
    }
}
