package slim3.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MemberAuthServiceTest extends AppEngineTestCase {

    private MemberAuthService service = new MemberAuthService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
