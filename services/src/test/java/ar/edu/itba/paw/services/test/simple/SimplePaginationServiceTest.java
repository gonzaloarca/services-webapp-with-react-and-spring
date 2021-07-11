package ar.edu.itba.paw.services.test.simple;

import ar.edu.itba.paw.interfaces.HirenetUtils;
import ar.edu.itba.paw.interfaces.services.JobCardService;
import ar.edu.itba.paw.models.JobPost;
import ar.edu.itba.paw.services.utils.SimplePaginationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class SimplePaginationServiceTest {

    private static final JobPost.Zone ZONE = JobPost.Zone.BELGRANO;
    private static final String QUERY = "queryqueryquery";

    @InjectMocks
    private final SimplePaginationService simplePaginationService = new SimplePaginationService();

    @Mock
    private JobCardService jobCardService;

    @Test
    public void testFindMaxPageJobPostsSearchWithoutCategory() {
        SimplePaginationService spy = Mockito.spy(simplePaginationService);

        spy.findJobCardsSearchMaxPage(QUERY, ZONE.ordinal(), HirenetUtils.SEARCH_WITHOUT_CATEGORIES, Locale.getDefault());

        Mockito.verify(jobCardService).searchMaxPage(Mockito.eq(QUERY), Mockito.eq(ZONE), Mockito.eq(Locale.getDefault()));
    }

    @Test
    public void testFindMaxPageJobPostsSearchWithCategory() {
        SimplePaginationService spy = Mockito.spy(simplePaginationService);

        spy.findJobCardsSearchMaxPage(QUERY, ZONE.ordinal(), JobPost.JobType.BABYSITTING.ordinal(), Locale.getDefault());

        Mockito.verify(jobCardService).searchWithCategoryMaxPage(Mockito.eq(QUERY), Mockito.eq(ZONE),
                Mockito.eq(JobPost.JobType.BABYSITTING), Mockito.eq(Locale.getDefault()));
    }
}
