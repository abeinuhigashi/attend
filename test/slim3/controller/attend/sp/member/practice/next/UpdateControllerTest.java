package slim3.controller.attend.sp.member.practice.next;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UpdateControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/attend/sp/member/practice/next/update");
        UpdateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
