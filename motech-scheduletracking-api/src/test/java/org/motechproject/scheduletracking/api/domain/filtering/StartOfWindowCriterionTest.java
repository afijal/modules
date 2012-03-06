package org.motechproject.scheduletracking.api.domain.filtering;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.WindowName;
import org.motechproject.scheduletracking.api.service.impl.EnrollmentService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.motechproject.util.DateUtil.newDateTime;
import static org.powermock.api.mockito.PowerMockito.when;

public class StartOfWindowCriterionTest {

    @Mock
    EnrollmentService enrollmentService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldFindEnrollmentsWhoseGivenWindowStartsDuringGivenTimeRange() {
        List<Enrollment> allEnrollments = new ArrayList<Enrollment>();
        Enrollment enrollment1 = mock(Enrollment.class);
        Enrollment enrollment2 = mock(Enrollment.class);
        Enrollment enrollment3 = mock(Enrollment.class);
        Enrollment enrollment4 = mock(Enrollment.class);
        allEnrollments.addAll(asList(enrollment1, enrollment2, enrollment3, enrollment4));

        when(enrollmentService.getStartOfWindowForCurrentMilestone(enrollment1, WindowName.due)).thenReturn(newDateTime(2012, 2, 3, 5, 10, 0));
        when(enrollmentService.getStartOfWindowForCurrentMilestone(enrollment2, WindowName.due)).thenReturn(newDateTime(2012, 2, 3, 0, 0, 0));
        when(enrollmentService.getStartOfWindowForCurrentMilestone(enrollment3, WindowName.due)).thenReturn(newDateTime(2012, 2, 5, 0, 0, 0));
        when(enrollmentService.getStartOfWindowForCurrentMilestone(enrollment4, WindowName.due)).thenReturn(newDateTime(2012, 2, 6, 0, 0, 0));

        DateTime start = newDateTime(2012, 2, 3, 0, 0, 0);
        DateTime end = newDateTime(2012, 2, 5, 23, 59, 59);
        List<Enrollment> filteredEnrollments = new StartOfWindowCriterion(WindowName.due, start, end, enrollmentService).filter(allEnrollments);
        assertEquals(asList(enrollment1, enrollment2, enrollment3), filteredEnrollments);
    }
}
