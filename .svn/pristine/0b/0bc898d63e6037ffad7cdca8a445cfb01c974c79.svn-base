package slim3.controller.attend.manage.practice.delete;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SubmitControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/attend/manage/practice/delete/submit");
        SubmitController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/attend/manage/practice/delete/submit.jsp"));
    }
}
