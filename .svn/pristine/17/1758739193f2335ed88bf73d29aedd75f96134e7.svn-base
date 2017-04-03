package slim3.controller.attend.manage.member.delete;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.model.Member;
import slim3.service.MemberService;

public class IndexController extends Controller {
    
    private MemberService memberService = new MemberService();

    @Override
    public Navigation run() throws Exception {
        
        if(asString("id") == null) {
            throw new IllegalArgumentException("IDÇ™nullÇ≈Ç∑ÅB");
        }
        
        Member member = memberService.searchFromId(asString("id"));
        
        if(member == null) {
            throw new IllegalArgumentException("çÌèúëŒè€ÇÃIDÇ™ë∂ç›ÇµÇ‹ÇπÇÒ. ID:" + asString("id"));
        }
        
        requestScope("member", member);
        
        return forward("index.jsp");
    }
}
