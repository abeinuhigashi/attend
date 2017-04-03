package slim3.controller.attend.manage.attendance.member.search;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SearchControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/attend/manage/attendance/member/search/search");
        SearchController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
