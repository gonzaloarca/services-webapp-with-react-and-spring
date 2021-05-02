package config;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@ComponentScan({"ar.edu.itba.paw.persistence",})
@Configuration
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
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
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


}
