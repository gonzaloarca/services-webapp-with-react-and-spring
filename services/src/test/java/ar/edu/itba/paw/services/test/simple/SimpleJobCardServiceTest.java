package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobCardDao;
import ar.edu.itba.paw.interfaces.services.JobCardService;
import ar.edu.itba.paw.interfaces.services.JobPostService;
import ar.edu.itba.paw.models.JobCard;
import ar.edu.itba.paw.models.JobPackage;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.JobPackageNotFoundException;
import ar.edu.itba.paw.models.exceptions.JobPostNotFoundException;
import ar.edu.itba.paw.services.simple.SimpleJobCardService;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.time.LocalDateTime;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobCardServiceTest {

    private static final JobPost.Zone ZONE = JobPost.Zone.BELGRANO;
    private static final JobPost.JobType TYPE = JobPost.JobType.CARPENTRY;
    private static final String QUERY = "queryqueryquery";
    private static final int PAGE = 1;
    private static final long POST_ID = 20;
    private static final long USER_ID = 8;
    private static final long FAKE_ID = 999;
    private static final long PACK_ID = 5;
    private static final int SIZE = 5;

    private static final List<JobPost.Zone> ZONES =
            new ArrayList<>(Arrays.asList(JobPost.Zone.values()[1],
                    JobPost.Zone.values()[2]));

    private final User PROFESSIONAL = new User(
            USER_ID, "franquesada@gmail.com", "Francisco Quesada", "0800111333", true, true, LocalDateTime.now());

    private final JobPost JOB_POST = new JobPost(
            POST_ID, PROFESSIONAL, "Plomero matriculado", "Lunes - Jueves de 09 a 16hrs", JobPost.JobType.PLUMBING,
            ZONES, true, LocalDateTime.now());

    private final JobCard JOB_CARD = new JobCard(JOB_POST, JobPackage.RateType.ONE_TIME, 1000.0,
            5, 4, 3.5, 1L);

    @InjectMocks
    private final SimpleJobCardService simpleJobCardService = new SimpleJobCardService();

    @Mock
    private JobCardDao jobCardDao;

    @Mock
    private MessageSource messageSource;

    @Mock
    private JobPostService jobPostService;

    @Mock
    private LevenshteinDistance levenshteinDistance;

    @Test
    public void testSearchWithoutCategory() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        Mockito.doReturn(new ArrayList<>()).when(spy).getSimilarTypes(QUERY, Locale.getDefault());

        spy.search(QUERY, ZONE.ordinal(), HirenetUtils.SEARCH_WITHOUT_CATEGORIES, 0, HirenetUtils.ALL_PAGES, Locale.getDefault());

        Mockito.verify(jobCardDao).search(Mockito.eq(QUERY), Mockito.eq(ZONE),
                Mockito.eq(new ArrayList<>()), Mockito.eq(JobCard.OrderBy.MOST_HIRED), Mockito.eq(HirenetUtils.ALL_PAGES));
    }

    @Test
    public void testSearchWithCategory() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        Mockito.doReturn(new ArrayList<>()).when(spy).getSimilarTypes(QUERY, Locale.getDefault());

        spy.search(QUERY, ZONE.ordinal(), JobPost.JobType.BABYSITTING.ordinal(), 0, HirenetUtils.ALL_PAGES, Locale.getDefault());

        Mockito.verify(jobCardDao).searchWithCategory(Mockito.eq(QUERY), Mockito.eq(ZONE),
                Mockito.eq(JobPost.JobType.BABYSITTING), Mockito.eq(new ArrayList<>()), Mockito.eq(JobCard.OrderBy.MOST_HIRED), Mockito.eq(HirenetUtils.ALL_PAGES));
    }

    @Test
    public void testGetSimilarTypesTest(){
        String similarType = "Plombero";
        String typeName = "Plomero";
        //Calculado con herramienta externa
        Mockito.when(messageSource.getMessage(Mockito.anyString(),Mockito.isNull(),Mockito.eq(Locale.getDefault()))).thenReturn(typeName);

        List<JobPost.JobType> similarTypes = simpleJobCardService.getSimilarTypes(similarType, Locale.getDefault());

        Assert.assertFalse(similarTypes.isEmpty());
        Assert.assertTrue(similarTypes.contains(JobPost.JobType.PLUMBING));
    }

    @Test
    public void findAllTest() {
        List<JobCard> jobCardList = new ArrayList<>();
        jobCardList.add(JOB_CARD);

        Mockito.when(jobCardDao.findAll(Mockito.eq(HirenetUtils.ALL_PAGES))).thenReturn(jobCardList);

        List<JobCard> result = simpleJobCardService.findAll();

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(JOB_CARD));
    }

    @Test
    public void findAllWithPageTest() {
        List<JobCard> jobCardList = new ArrayList<>();
        jobCardList.add(JOB_CARD);

        Mockito.when(jobCardDao.findAll(Mockito.eq(PAGE))).thenReturn(jobCardList);

        List<JobCard> result = simpleJobCardService.findAll(PAGE);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(JOB_CARD));
    }

    @Test
    public void findByUserIdTest() {
        List<JobCard> jobCardList = new ArrayList<>();
        jobCardList.add(JOB_CARD);

        Mockito.when(jobCardDao.findByUserId(Mockito.eq(USER_ID), Mockito.eq(HirenetUtils.ALL_PAGES)))
                .thenReturn(jobCardList);

        List<JobCard> result = simpleJobCardService.findByUserId(USER_ID);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(JOB_CARD));
    }

    @Test
    public void findByUserIdWithPageTest() {
        List<JobCard> jobCardList = new ArrayList<>();
        jobCardList.add(JOB_CARD);

        Mockito.when(jobCardDao.findByUserId(Mockito.eq(USER_ID), Mockito.eq(PAGE)))
                .thenReturn(jobCardList);

        List<JobCard> result = simpleJobCardService.findByUserId(USER_ID, PAGE);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(JOB_CARD));
    }

    @Test
    public void findByPostIdSuccessTest() {
        Mockito.when(jobCardDao.findByPostId(Mockito.eq(POST_ID)))
                .thenReturn(Optional.of(JOB_CARD));

        JobCard result = simpleJobCardService.findByPostId(POST_ID);

        Assert.assertNotNull(result);
        Assert.assertEquals(JOB_CARD, result);
    }

    @Test(expected = JobPostNotFoundException.class)
    public void findByPostIdExceptionTest() {
        Mockito.when(jobCardDao.findByPostId(Mockito.eq(FAKE_ID)))
                .thenReturn(Optional.empty());

        simpleJobCardService.findByPostId(FAKE_ID);
    }

    @Test
    public void findByPackageIdWithPackageInfoWithInactiveSuccessTest() {
        Mockito.when(jobCardDao.findByPackageIdWithPackageInfoWithInactive(Mockito.eq(PACK_ID)))
                .thenReturn(Optional.of(JOB_CARD));

        JobCard result = simpleJobCardService.findByPackageIdWithPackageInfoWithInactive(PACK_ID);

        Assert.assertNotNull(result);
        Assert.assertEquals(JOB_CARD, result);
    }

    @Test(expected = JobPackageNotFoundException.class)
    public void findByPackageIdWithPackageInfoWithInactiveExceptionTest() {
        Mockito.when(jobCardDao.findByPackageIdWithPackageInfoWithInactive(Mockito.eq(FAKE_ID)))
                .thenReturn(Optional.empty());

        simpleJobCardService.findByPackageIdWithPackageInfoWithInactive(FAKE_ID);
    }

    @Test
    public void findByPostIdWithInactiveSuccessTest() {
        Mockito.when(jobCardDao.findByPostIdWithInactive(Mockito.eq(POST_ID)))
                .thenReturn(Optional.of(JOB_CARD));

        JobCard result = simpleJobCardService.findByPostIdWithInactive(POST_ID);

        Assert.assertNotNull(result);
        Assert.assertEquals(JOB_CARD, result);
    }

    @Test(expected = JobPostNotFoundException.class)
    public void findByPostIdWithInactiveExceptionTest() {
        Mockito.when(jobCardDao.findByPostIdWithInactive(Mockito.eq(FAKE_ID)))
                .thenReturn(Optional.empty());

        simpleJobCardService.findByPostIdWithInactive(FAKE_ID);
    }

    @Test
    public void findRelatedJobCardsTest() {
        List<JobCard> jobCardList = new ArrayList<>();
        jobCardList.add(JOB_CARD);

        Mockito.when(jobCardDao.findRelatedJobCards(Mockito.eq(USER_ID), Mockito.eq(PAGE)))
                .thenReturn(jobCardList);

        List<JobCard> result = simpleJobCardService.findRelatedJobCards(USER_ID, PAGE);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.contains(JOB_CARD));
    }

    @Test
    public void findSizeByUserIdTest() {
        Mockito.when(jobPostService.findSizeByUserId(Mockito.eq(USER_ID)))
                .thenReturn(SIZE);

        int result = simpleJobCardService.findByUserIdSize(USER_ID);

        Assert.assertEquals(SIZE, result);
    }

    @Test
    public void findMaxPageByUserIdTest() {
        Mockito.when(jobCardDao.findByUserIdMaxPage(Mockito.eq(USER_ID)))
                .thenReturn(PAGE);

        int result = simpleJobCardService.findByUserIdMaxPage(USER_ID);

        Assert.assertEquals(PAGE, result);
    }

    @Test
    public void findMaxPageSearchTest() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        List<JobPost.JobType> jobTypeList = new ArrayList<>();
        jobTypeList.add(TYPE);

        Mockito.doReturn(jobTypeList).when(spy).getSimilarTypes(QUERY, Locale.getDefault());

        Mockito.when(jobCardDao.searchMaxPage(Mockito.eq(QUERY), Mockito.eq(ZONE), Mockito.eq(jobTypeList)))
                .thenReturn(PAGE);

        int result = spy.searchMaxPage(QUERY, ZONE.ordinal(), HirenetUtils.SEARCH_WITHOUT_CATEGORIES, Locale.getDefault());

        Assert.assertEquals(PAGE, result);
    }

    @Test
    public void findMaxPageSearchWithCategoryTest() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        List<JobPost.JobType> jobTypeList = new ArrayList<>();
        jobTypeList.add(TYPE);

        Mockito.doReturn(jobTypeList).when(spy).getSimilarTypes(QUERY, Locale.getDefault());

        Mockito.when(jobCardDao.searchWithCategoryMaxPage(Mockito.eq(QUERY), Mockito.eq(ZONE), Mockito.eq(TYPE),
                Mockito.eq(jobTypeList)))
                .thenReturn(PAGE);

        int result = spy.searchMaxPage(QUERY, ZONE.ordinal(), TYPE.ordinal(), Locale.getDefault());

        Assert.assertEquals(PAGE, result);
    }

    @Test
    public void findMaxPageRelatedJobCardsTest() {
        Mockito.when(jobCardDao.findRelatedJobCardsMaxPage(Mockito.eq(USER_ID)))
                .thenReturn(PAGE);

        int result = simpleJobCardService.findRelatedJobCardsMaxPage(USER_ID);

        Assert.assertEquals(PAGE, result);
    }

}
