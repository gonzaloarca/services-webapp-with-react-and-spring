package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobCardDao;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.services.simple.SimpleJobCardService;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobCardServiceTest {

    private static final JobPost.Zone ZONE = JobPost.Zone.BELGRANO;
    private static final String QUERY = "queryqueryquery";

    @InjectMocks
    private final SimpleJobCardService simpleJobCardService = new SimpleJobCardService();

    @Mock
    private JobCardDao jobCardDao;

    @Mock
    private MessageSource messageSource;

    @Mock
    private LevenshteinDistance levenshteinDistance;

    @Test
    public void testSearchWithoutCategory() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        Mockito.doReturn(new ArrayList<>()).when(spy).getSimilarTypes(QUERY, Locale.getDefault());

        spy.search(QUERY, ZONE.ordinal(), HirenetUtils.SEARCH_WITHOUT_CATEGORIES, null, HirenetUtils.ALL_PAGES, Locale.getDefault());

        Mockito.verify(jobCardDao).search(Mockito.eq(QUERY), Mockito.eq(ZONE),
                Mockito.eq(new ArrayList<>()), Mockito.eq(null), Mockito.eq(HirenetUtils.ALL_PAGES));
    }

    @Test
    public void testSearchWithCategory() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        Mockito.doReturn(new ArrayList<>()).when(spy).getSimilarTypes(QUERY, Locale.getDefault());

        spy.search(QUERY, ZONE.ordinal(), JobPost.JobType.BABYSITTING.ordinal(), null, HirenetUtils.ALL_PAGES, Locale.getDefault());

        Mockito.verify(jobCardDao).searchWithCategory(Mockito.eq(QUERY), Mockito.eq(ZONE),
                Mockito.eq(JobPost.JobType.BABYSITTING), Mockito.eq(new ArrayList<>()), Mockito.eq(null), Mockito.eq(HirenetUtils.ALL_PAGES));
    }

    @Test
    public void testGetSimilarTypesTest(){
        String similarType = "Plombero";
        String typeName = "Plomero";
        //Calculado con herramienta externa
        int levenshtein = 1;
        Mockito.when(messageSource.getMessage(Mockito.anyString(),Mockito.isNull(),Mockito.eq(Locale.getDefault()))).thenReturn(typeName);
        Mockito.when(levenshteinDistance.apply(Mockito.eq(similarType.toLowerCase()),Mockito.eq(typeName))).thenReturn(levenshtein);

        List<JobPost.JobType> similarTypes = simpleJobCardService.getSimilarTypes(similarType, Locale.getDefault());

        Assert.assertFalse(similarTypes.isEmpty());
        Assert.assertTrue(similarTypes.contains(JobPost.JobType.PLUMBING));
    }
}
