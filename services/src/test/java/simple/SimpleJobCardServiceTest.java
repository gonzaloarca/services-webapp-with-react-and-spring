package simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.dao.JobCardDao;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.services.simple.SimpleJobCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class SimpleJobCardServiceTest {

    private static final JobPost.Zone ZONE = JobPost.Zone.BELGRANO;
    private static final String QUERY = "queryqueryquery";

    @InjectMocks
    private final SimpleJobCardService simpleJobCardService = new SimpleJobCardService();

    @Mock
    private JobCardDao jobCardDao;

    @Test
    public void testSearchWithoutCategory() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        Mockito.doReturn(new ArrayList<>()).when(spy).getSimilarTypes(QUERY);

        spy.search(QUERY, ZONE.ordinal(), HirenetUtils.SEARCH_WITHOUT_CATEGORIES, HirenetUtils.ALL_PAGES);

        Mockito.verify(jobCardDao).search(Mockito.eq(QUERY), Mockito.eq(ZONE),
                Mockito.eq(new ArrayList<>()), Mockito.eq(HirenetUtils.ALL_PAGES));
    }

    @Test
    public void testSearchWithCategory() {
        SimpleJobCardService spy = Mockito.spy(simpleJobCardService);
        Mockito.doReturn(new ArrayList<>()).when(spy).getSimilarTypes(QUERY);

        spy.search(QUERY, ZONE.ordinal(), JobPost.JobType.BABYSITTING.ordinal(), HirenetUtils.ALL_PAGES);

        Mockito.verify(jobCardDao).searchWithCategory(Mockito.eq(QUERY), Mockito.eq(ZONE),
                Mockito.eq(JobPost.JobType.BABYSITTING), Mockito.eq(new ArrayList<>()), Mockito.eq(HirenetUtils.ALL_PAGES));
    }
}
