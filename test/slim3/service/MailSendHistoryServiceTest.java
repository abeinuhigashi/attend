package slim3.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MailSendHistoryServiceTest extends AppEngineTestCase {

    private MailSendHistoryService service = new MailSendHistoryService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
