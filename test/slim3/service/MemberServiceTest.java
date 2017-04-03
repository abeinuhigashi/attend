package slim3.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.slim3.util.DateUtil;

import slim3.model.Member;

public class MemberServiceTest extends AppEngineTestCase {

    private MemberService service = new MemberService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
    
    @Before
    public void CreateTestData() throws Exception {
        Member m = null;
        m = new Member();
        m.setId("test1");
        m.setFirstName("taro");
        m.setLastName("test");
        m.setBirthDay(DateUtil.toDate("2010-10-01", DateUtil.ISO_DATE_PATTERN));
        m.setMailAddress("test@test.com");
        Datastore.put(m);

        m = new Member();
        m.setId("test2");
        m.setFirstName("ziro");
        m.setLastName("test");
        Datastore.put(m);
        
        m = new Member();
        m.setId("test3");
        m.setFirstName("savuroh");
        m.setLastName("test");
        Datastore.put(m);        
    }
}