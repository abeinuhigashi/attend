package slim3.controller.attend.batch;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.model.Member;
import slim3.model.MemberAuth;
import slim3.service.MemberAuthService;
import slim3.service.MemberService;

public class TestController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        MemberService memberSvc = new MemberService();
        Member member = new Member();

        member.setId("test");
        member.setLastName("–¼Žš");
        member.setFirstName("–¼‘O");
        member.setPassword("password");

        memberSvc.regist(member);
        MemberAuthService memberAuthSvc = new MemberAuthService();

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAttendance(Constants.AUTH_UPDATE);
        memberAuth.setMember(Constants.AUTH_UPDATE);
        memberAuth.setPractice(Constants.AUTH_UPDATE);
        memberAuth.setMemberAuth(Constants.AUTH_UPDATE);
        memberAuth.getMemberRef().setModel(member);

        memberAuthSvc.regist(memberAuth);
        return null;
    }
}
