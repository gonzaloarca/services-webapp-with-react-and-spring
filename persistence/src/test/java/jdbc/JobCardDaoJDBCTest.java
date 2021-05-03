package jdbc;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jdbc.*;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:db_data_test.sql")
public class JobCardDaoJDBCTest {

    private static final User USER1 = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true,
            LocalDateTime.now());

    private static final User USER2 = new User(
            2,
            "manurodriguez@gmail.com",
            "Manuel Rodriguez",
            "1109675432",
            true,
            true,
            LocalDateTime.now());

    private static final List<JobPost.Zone> ZONES_USER2 = new ArrayList<>(
            Arrays.asList(
                    JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]
            )
    );
    private static final JobPost JOB_POST_USER2 = new JobPost(
            3,
            USER2,
            "Electricista no matriculado", "Lun a Jueves 13hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES_USER2,
            0.0,
            true,
            LocalDateTime.now());
    private static final JobPackage JOB_PACKAGE_USER2 = new JobPackage(
            4,
            JOB_POST_USER2.getId(),
            "Trabajo barato",
            "Arreglos varios",
            500.00, JobPackage.RateType.values()[0],
            true
    );
    private static int CONTRACTS_COMPLETED_JOB_POST_USER2 = 4;
    private static int REVIEWS_COUNT_JOB_POST_USER2 = 0;
    private static final JobCard JOB_CARD_USER2 = new JobCard(
            JOB_POST_USER2,
            JOB_PACKAGE_USER2.getRateType(),
            JOB_PACKAGE_USER2.getPrice(),
            null,
            CONTRACTS_COMPLETED_JOB_POST_USER2,
            REVIEWS_COUNT_JOB_POST_USER2
    );

    @Autowired
    private DataSource ds;

    @InjectMocks
    @Autowired
    private JobCardDaoJDBC jobCardDaoJDBCTest;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testFindRelatedJobCards() {
        List<JobCard> maybeJobCards = jobCardDaoJDBCTest.findRelatedJobCards(1, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(maybeJobCards.isEmpty());
        Assert.assertEquals(1, maybeJobCards.size());
        Assert.assertEquals(JOB_CARD_USER2, maybeJobCards.get(0));
    }
}
