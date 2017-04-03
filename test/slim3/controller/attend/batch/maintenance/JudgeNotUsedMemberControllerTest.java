package slim3.controller.attend.batch.maintenance;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class JudgeNotUsedMemberControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/attend/batch/maintenance/JudgeNotUsedMember");
        JudgeNotUsedMemberController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
