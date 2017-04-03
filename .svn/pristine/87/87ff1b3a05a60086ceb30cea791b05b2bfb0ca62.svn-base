package slim3.controller.attend.manage.member.search;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.model.Member;
import slim3.service.MemberService;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        
        MemberService svc = new MemberService();
        
        List<Member> list = svc.getAll();
        
        requestScope("memberList", list);
        
        return forward("index.jsp");
    }
}
