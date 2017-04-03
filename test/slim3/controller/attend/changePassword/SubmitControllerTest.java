package slim3.controller.attend.changePassword;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SubmitControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/attend/changePassword/submit");
        SubmitController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/attend/changePassword/submit.jsp"));
    }
}
