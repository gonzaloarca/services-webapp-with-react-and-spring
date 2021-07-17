package ar.edu.itba.paw.persistence.test.jpa;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.JobCardDaoJpa;
import ar.edu.itba.paw.persistence.test.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:job_card_data_test.sql")
@Transactional
public class JobCardDaoJpaTest {

    private static final User USER1 = new User(
            1,
            "franquesada@gmail.com",
            "Francisco Quesada",
            "1147895678",
            true,
            true,
            LocalDateTime.now());
    private static final User USER2 = new User(
            3,
            "gonzaarca@gmail.com",
            "Gonzalo Arca",
            "0549940406521",
            true,
            true,
            LocalDateTime.now());
    private static final User USER3 = new User(
            8,
            "npapa@gmail.com",
            "Nicolas Papa",
            "09876654321354",
            true,
            true,
            LocalDateTime.now());
    private static final User USER4 = new User(
            11,
            "tren@gmail.com",
            "Soledad del Cielo",
            "87876767",
            true,
            true,
            LocalDateTime.now());

    private static final List<JobPost.Zone> ZONES_USER = new ArrayList<JobPost.Zone>(
            Arrays.asList(
                    JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]
            )
    );
    private static final JobPost JOB_POST_USER2 = new JobPost(
            11,
            USER2,
            "Electricista no matriculado", "Lun a Jueves 13hs - 14hs",
            JobPost.JobType.values()[1],
            ZONES_USER,
            true,
            LocalDateTime.now());
    private static final JobPackage JOB_PACKAGE_USER2 = new JobPackage(
            19,
            JOB_POST_USER2,
            "Trabajo simple",
            "Arreglos de tomacorrientes",
            200.00, JobPackage.RateType.values()[0],
            true
    );
    private static final JobCard JOB_CARD_USER2 = new JobCard(
            JOB_POST_USER2,
            JOB_PACKAGE_USER2.getRateType(),
            JOB_PACKAGE_USER2.getPrice(),
            0,
            2,
            0.0,
            null
    );
    private static final JobPost JOB_POST_USER3 = new JobPost(
            12,
            USER3,
            "Paseador de gatos", "Sabados de 8hs - 14hs",
            JobPost.JobType.values()[3],
            ZONES_USER,
            true,
            LocalDateTime.now());
    private static final JobPackage JOB_PACKAGE_USER3 = new JobPackage(
            20,
            JOB_POST_USER3,
            "Trabajo simple",
            "Paseo tardio",
            300.00, JobPackage.RateType.values()[0],
            true
    );
    private static final JobCard JOB_CARD_USER3 = new JobCard(
            JOB_POST_USER3,
            JOB_PACKAGE_USER3.getRateType(),
            JOB_PACKAGE_USER3.getPrice(),
            0,
            2,
            0.0,
            null
    );
    private static final JobPost JOB_POST_USER4 = new JobPost(
            13,
            USER4,
            "Paseador de urones", "Domingos de 8hs - 14hs",
            JobPost.JobType.values()[3],
            ZONES_USER,
            true,
            LocalDateTime.now());
    private static final JobPost JOB_POST_INACTIVE = new JobPost(
            10,
            USER1,
            "Plomero Inactivo", "Miercoles a Viernes 10hs - 14hs",
            JobPost.JobType.values()[2],
            ZONES_USER,
            false,
            LocalDateTime.now());
    private static final JobPackage JOB_PACKAGE_USER4 = new JobPackage(
            21,
            JOB_POST_USER4,
            "Trabajo simple",
            "Paseo recreativo",
            300.00, JobPackage.RateType.values()[0],
            true
    );
    private static final JobPackage JOB_PACKAGE_JOB_POST_INACTIVE = new JobPackage(
            18,
            JOB_POST_INACTIVE,
            "Trabajo simple",
            "Arreglos de tomacorrientes",
            200.00, JobPackage.RateType.values()[0],
            true
    );
    private static final JobCard JOB_CARD_USER4 = new JobCard(
            JOB_POST_USER4,
            JOB_PACKAGE_USER4.getRateType(),
            JOB_PACKAGE_USER4.getPrice(),
            0,
            2,
            0.0,
            null
    );
    private static final JobCard JOB_CARD_JOB_POST_INACTIVE = new JobCard(
            JOB_POST_INACTIVE,
            JOB_PACKAGE_USER4.getRateType(),
            JOB_PACKAGE_USER4.getPrice(),
            0,
            2,
            0.0,
            null
    );
    private static final int RELATED_JOB_CARDS_COUNT = 3;
    private static final int SEARCH_ELECTRICISTA_COUNT = 4;
    private static final int CATEGORY_ELECTRICITY_COUNT = 4;
    private static final int ELECTRICITY_AND_CARPENTRY_POST_COUNT = 8;
    private static final int NON_EXISTING_ID = 9999;
    private static final int TOTAL_JOB_CARDS_ACTIVE = 12;
    private static final int TOTAL_JOB_CARDS_USER_ID_1 = 10;
    private static final int TOTAL_JOB_CARDS_USER_ID_3 = 1;

    @Autowired
    private JobCardDaoJpa jobCardDaoJpa;

    @Test
    public void testFindRelatedJobCards() {

        List<JobCard> maybeJobCards = jobCardDaoJpa.findRelatedJobCards(1, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(maybeJobCards.isEmpty());
        Assert.assertEquals(RELATED_JOB_CARDS_COUNT, maybeJobCards.size());
        Assert.assertEquals(JOB_CARD_USER3, maybeJobCards.get(0));  //aparece primero pues tiene mas clientes en comun
        Assert.assertEquals(JOB_CARD_USER2, maybeJobCards.get(1));  //aparece segundo por que a pesar de tener los mismos clientes que 4, tiene mas contratos completados
        Assert.assertEquals(JOB_CARD_USER4, maybeJobCards.get(2));
    }

    @Test
    public void testFindRelatedJobCardsNonExistingProId() {

        List<JobCard> maybeJobCards = jobCardDaoJpa.findRelatedJobCards(NON_EXISTING_ID, HirenetUtils.ALL_PAGES);

        Assert.assertTrue(maybeJobCards.isEmpty());
    }

    @Test
    public void testSearch() {
        String title = "Electricista";
        JobPost.Zone zone = JobPost.Zone.values()[1];

        List<JobCard> jobCards = jobCardDaoJpa.search(title, zone, new ArrayList<>(), JobCard.OrderBy.BETTER_QUALIFIED, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobCards.isEmpty());
        Assert.assertEquals(SEARCH_ELECTRICISTA_COUNT, jobCards.size());
    }

    @Test(expected = NullPointerException.class)
    public void testSearchWithNullZone() {
        String title = "Electricista";

        jobCardDaoJpa.search(title, null, new ArrayList<>(), JobCard.OrderBy.BETTER_QUALIFIED, HirenetUtils.ALL_PAGES);

    }

    @Test(expected = NullPointerException.class)
    public void testSearchWithNullSimilarTypes() {
        String title = "Electricista";
        JobPost.Zone zone = JobPost.Zone.values()[1];

        jobCardDaoJpa.search(title, zone, null, JobCard.OrderBy.BETTER_QUALIFIED, HirenetUtils.ALL_PAGES);
    }

    @Test
    public void testSearchWithSimilarTypes() {
        String title = "electr";
        JobPost.Zone zone = JobPost.Zone.values()[1];

        List<JobCard> jobCards = jobCardDaoJpa.search(title, zone, new ArrayList<>(Collections.singletonList(JobPost.JobType.values()[2])), JobCard.OrderBy.BETTER_QUALIFIED, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobCards.isEmpty());
        Assert.assertEquals(ELECTRICITY_AND_CARPENTRY_POST_COUNT, jobCards.size());
    }

    @Test
    public void testSearchWithCategory() {
        String title = "";
        JobPost.Zone zone = JobPost.Zone.values()[1];
        JobPost.JobType jobType = JobPost.JobType.ELECTRICITY;

        List<JobCard> jobCards = jobCardDaoJpa.searchWithCategory(title, zone, jobType, new ArrayList<>(), JobCard.OrderBy.BETTER_QUALIFIED, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobCards.isEmpty());
        Assert.assertEquals(CATEGORY_ELECTRICITY_COUNT, jobCards.size());
    }

    @Test
    public void testSearchWithSimilarTypesCategory() {
        String title = "ELECT";
        JobPost.Zone zone = JobPost.Zone.values()[1];
        JobPost.JobType jobType = JobPost.JobType.ELECTRICITY;

        List<JobCard> jobCards = jobCardDaoJpa.searchWithCategory(title, zone, jobType, new ArrayList<>(Collections.singletonList(JobPost.JobType.ELECTRICITY)), JobCard.OrderBy.BETTER_QUALIFIED, HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobCards.isEmpty());
        Assert.assertEquals(CATEGORY_ELECTRICITY_COUNT, jobCards.size());
    }

    @Test
    public void findAllTest() {

        List<JobCard> jobCards = jobCardDaoJpa.findAll(HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobCards.isEmpty());
        Assert.assertEquals(12, jobCards.size());
    }

    @Test
    public void findByUserIdTest() {

        List<JobCard> jobCards = jobCardDaoJpa.findByUserId(USER1.getId(), HirenetUtils.ALL_PAGES);

        Assert.assertFalse(jobCards.isEmpty());
        Assert.assertEquals(9, jobCards.size());
    }

    @Test
    public void findByNonExistingUserIdTest() {

        List<JobCard> jobCards = jobCardDaoJpa.findByUserId(NON_EXISTING_ID, HirenetUtils.ALL_PAGES);

        Assert.assertTrue(jobCards.isEmpty());
    }

    @Test
    public void findByPostIdTest() {

        Optional<JobCard> jobCard = jobCardDaoJpa.findByPostId(1);

        Assert.assertTrue(jobCard.isPresent());
        Assert.assertEquals(jobCard.get().getJobPost().getId(), 1);
    }

    @Test
    public void findByNonExistingPostIdTest() {

        Optional<JobCard> jobCard = jobCardDaoJpa.findByPostId(NON_EXISTING_ID);

        Assert.assertFalse(jobCard.isPresent());
    }

    @Test
    public void testFindMaxPageByUserId1() {

        int maxPage = jobCardDaoJpa.findByUserIdMaxPage(USER1.getId());

        Assert.assertEquals(Math.ceil((double) TOTAL_JOB_CARDS_USER_ID_1 / HirenetUtils.PAGE_SIZE), maxPage, 0.0000001);
    }

    @Test
    public void testFindMaxPageByUserId3() {

        int maxPage = jobCardDaoJpa.findByUserIdMaxPage(USER2.getId());

        Assert.assertEquals(Math.ceil((double) TOTAL_JOB_CARDS_USER_ID_3 / HirenetUtils.PAGE_SIZE), maxPage, 0.0000001);
    }

    @Test
    public void testFindMaxPageByNonExistingUserId() {

        int maxPage = jobCardDaoJpa.findByUserIdMaxPage(NON_EXISTING_ID);

        Assert.assertEquals(0, maxPage);
    }

    @Test
    public void testFindMaxPageSearch() {
        String title = "Electricista";
        JobPost.Zone zone = JobPost.Zone.values()[1];

        int maxPage = jobCardDaoJpa.searchMaxPage(title, zone, new ArrayList<>());

        Assert.assertEquals(Math.ceil((double) SEARCH_ELECTRICISTA_COUNT / HirenetUtils.PAGE_SIZE), maxPage, 0.0000001);
    }

    @Test
    public void testFindMaxPageSearchWithCategory() {
        String title = "";
        JobPost.Zone zone = JobPost.Zone.values()[1];
        JobPost.JobType jobType = JobPost.JobType.ELECTRICITY;

        int maxPage = jobCardDaoJpa.searchWithCategoryMaxPage(title, zone, jobType, new ArrayList<>());

        Assert.assertEquals(Math.ceil((double) SEARCH_ELECTRICISTA_COUNT / HirenetUtils.PAGE_SIZE), maxPage, 0.0000001);
    }

    @Test
    public void testFindMaxPageRelatedJobCards() {

        int maxPage = jobCardDaoJpa.findRelatedJobCardsMaxPage(1);

        Assert.assertEquals(Math.ceil((double) RELATED_JOB_CARDS_COUNT / HirenetUtils.PAGE_SIZE), maxPage, 0.0000001);
    }


    @Test
    public void testFindByPackageIdWithPackageInfoWithInactive() {

        Optional<JobCard> maybeJobCard = jobCardDaoJpa.findByPackageIdWithPackageInfoWithInactive(JOB_PACKAGE_USER2.getId());

        Assert.assertTrue(maybeJobCard.isPresent());
        Assert.assertEquals(JOB_CARD_USER2, maybeJobCard.get());
    }

    @Test
    public void testFindInactiveByPackageIdWithPackageInfoWithInactive() {
        //busca un job_post inactive

        Optional<JobCard> maybeJobCard = jobCardDaoJpa.findByPackageIdWithPackageInfoWithInactive(JOB_PACKAGE_JOB_POST_INACTIVE.getId());

        Assert.assertTrue(maybeJobCard.isPresent());
        Assert.assertEquals(JOB_CARD_JOB_POST_INACTIVE, maybeJobCard.get());
    }

    @Test
    public void testFindByNonExistingPackageIdWithPackageInfoWithInactive() {

        Optional<JobCard> maybeJobCard = jobCardDaoJpa.findByPackageIdWithPackageInfoWithInactive(NON_EXISTING_ID);

        Assert.assertFalse(maybeJobCard.isPresent());
    }

    @Test
    public void testFindInactiveByPostIdWithInactive() {

        Optional<JobCard> maybeJobCard = jobCardDaoJpa.findByPostIdWithInactive(JOB_POST_INACTIVE.getId());

        Assert.assertTrue(maybeJobCard.isPresent());
        Assert.assertEquals(JOB_CARD_JOB_POST_INACTIVE, maybeJobCard.get());
    }

    @Test
    public void testFindInactiveByNonExistingPostIdWithInactive() {

        Optional<JobCard> maybeJobCard = jobCardDaoJpa.findByPackageIdWithPackageInfoWithInactive(NON_EXISTING_ID);

        Assert.assertFalse(maybeJobCard.isPresent());
    }

}
