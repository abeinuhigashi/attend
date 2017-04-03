package slim3.controller.attend.manage.attendance.member;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DispControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/attend/manage/attendance/member/disp");
        DispController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/attend/manage/attendance/member/disp.jsp"));
    }
}
