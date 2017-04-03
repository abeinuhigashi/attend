package slim3.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;
import org.slim3.util.DateUtil;

import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;

public class AttendanceServiceTest extends AppEngineTestCase {

    private AttendanceService service = new AttendanceService();
    private MemberService memberSvc = new MemberService();
    private PracticeService practiceSvc = new PracticeService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }

    @Test
    public void testRegistListAndDeleteList() throws Exception {
        Attendance a = null;
        Member m = null;
        Practice p = null;
        List<Attendance> attendanceList = new ArrayList<Attendance>();
        
        a = new Attendance();
        a.setAttendance(1);
        a.setFinished(false);
        a.setRacital("It's racital, too.");
        m = memberSvc.searchFromId("test3");
        a.setMemberKey(m.getKey());
        p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        a.setPracticeKey(p.getKey());
        attendanceList.add(a);
               
        a = new Attendance();
        a.setAttendance(0);
        a.setFinished(true);
        a.setRacital("It's racital, too.");
        m = memberSvc.searchFromId("test3");
        a.setMemberKey(m.getKey());
        p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-12-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        a.setPracticeKey(p.getKey());
        attendanceList.add(a);
  
        attendanceList = service.registList(attendanceList);
        
        assertNotNull(service.searchFromMemberIdAndPracticeDate("test3", DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        assertNotNull(service.searchFromMemberIdAndPracticeDate("test3", DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        
        service.deleteList(attendanceList);
        
        assertNull(service.searchFromMemberIdAndPracticeDate("test3", DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        assertNull(service.searchFromMemberIdAndPracticeDate("test3", DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));        
    }
    
    @Test
    public void testSearchFromMemberId() throws Exception {
        assertEquals(3, service.searchFromMemberId("test1").size());
        assertEquals(1, service.searchFromMemberId("test2").size());
        assertNull(service.searchFromMemberId("testXXX"));
        assertNull(service.searchFromMemberId(null));
    }
    
    @Test
    public void testSearchFromKey() throws Exception {
        Member m = memberSvc.searchFromId("test1");
        Practice p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        assertNotNull(service.searchFromKey(service.generateKey(m.getKey(), p.getKey())));
        assertNull(service.searchFromKey(Datastore.createKey(Attendance.class, "XXX")));
        assertNull(service.searchFromKey(null));
    }
    
    @Test
    public void testSearchFromPracticeDate() throws Exception {
        assertEquals(2, service.searchFromPracticeDate(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)).size());
        assertEquals(1, service.searchFromPracticeDate(DateUtil.toDate("2010-12-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)).size());
        assertNull(service.searchFromPracticeDate(DateUtil.toDate("2010-11-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        assertNull(service.searchFromPracticeDate(null));
    }    
        
    @Test
    public void testSearchFromMemberIdAndPracticeDate() throws Exception {
        assertNotNull(service.searchFromMemberIdAndPracticeDate("test1", DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        assertNull(service.searchFromMemberIdAndPracticeDate("testXXX", DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        assertNull(service.searchFromMemberIdAndPracticeDate("test2", DateUtil.toDate("2010-11-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        assertNull(service.searchFromMemberIdAndPracticeDate("test1", null));
        assertNull(service.searchFromMemberIdAndPracticeDate(null, DateUtil.toDate("2010-11-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
    }    

    @Test
    public void testGetInitalizedAttendance(){
        Attendance attendance = service.getInitalizedAttendance(memberSvc.searchFromId("test3"), practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)));
        assertEquals(0, attendance.getAttendance());
        assertEquals(memberSvc.searchFromId("test3").getKey(), attendance.getMemberKey());
        assertEquals(practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN)).getKey(), attendance.getPracticeKey());
        assertEquals(false, attendance.getFinished());
        assertEquals(false, attendance.getInputFlg());
        assertNull(attendance.getRacital());
    }
    
    @Before
    public void CreateDate() {
        
        Member m = null;
        Practice p = null;
        Attendance a = null;
        
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
        
        p = new Practice();
        p.setStartDate(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        p.setEndDate(DateUtil.toDate("2010-10-01T12:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        p.setGatheringDate(DateUtil.toDate("2010-10-01T09:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        p.setPracticePlace("test praza");
        p.setGatheringPoint("front of test praza");
        p.setRecital("It's recital.");
        practiceSvc.regist(p);
        
        p = new Practice();
        p.setStartDate(DateUtil.toDate("2010-10-11T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        p.setEndDate(DateUtil.toDate("2010-10-11T12:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        p.setGatheringDate(DateUtil.toDate("2010-10-11T09:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        practiceSvc.regist(p);
        
        p = new Practice();
        p.setStartDate(DateUtil.toDate("2010-12-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        p.setEndDate(DateUtil.toDate("2010-12-01T12:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        p.setGatheringDate(DateUtil.toDate("2010-12-01T09:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        practiceSvc.regist(p);
        
        a = new Attendance();
        a.setAttendance(1);
        a.setFinished(false);
        a.setRacital("It's racital, too.");
        m = memberSvc.searchFromId("test1");
        a.setMemberKey(m.getKey());
        a.getMemberRef().setModel(m);
        p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        a.getPracticeRef().setModel(p);
        a.setPracticeKey(p.getKey());
        service.regist(a);
                
        a = new Attendance();
        a.setAttendance(1);
        a.setFinished(false);
        a.setRacital("It's racital, too.");
        m = memberSvc.searchFromId("test1");
        a.setMemberKey(m.getKey());
        p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-12-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        a.setPracticeKey(p.getKey());
        service.regist(a);
               
        a = new Attendance();
        a.setAttendance(0);
        a.setFinished(true);
        a.setRacital("It's racital, too.");
        m = memberSvc.searchFromId("test2");
        a.setMemberKey(m.getKey());
        p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        a.setPracticeKey(p.getKey());
        service.regist(a);

        a = new Attendance();
        a.setAttendance(0);
        a.setFinished(true);
        a.setRacital("It's racital, too.");
        m = memberSvc.searchFromId("test2");
        a.setMemberKey(m.getKey());
        p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        a.setPracticeKey(p.getKey());
        service.regist(a);

        a = new Attendance();
        a.setAttendance(0);
        a.setFinished(true);
        a.setRacital("It's racital, too.");
        m = memberSvc.searchFromId("test1");
        a.setMemberKey(m.getKey());
        p = practiceSvc.searchFromStartDateTime(DateUtil.toDate("2010-10-11T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        a.setPracticeKey(p.getKey());
        service.regist(a);    
    }
    
    @After
    public void DeleteData() {
        memberSvc.delete("test1");
        memberSvc.delete("test2");
        memberSvc.delete("test3");
        
        practiceSvc.delete(DateUtil.toDate("2010-10-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));
        practiceSvc.delete(DateUtil.toDate("2010-12-01T10:00:00", DateUtil.ISO_DATE_TIME_PATTERN));    }
}
