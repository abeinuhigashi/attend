package slim3.controller.attend.manage.member.delete;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.service.MemberService;

public class SubmitController extends Controller {
    
    private MemberService memberService = new MemberService();

    @Override
    public Navigation run() throws Exception {

        if (asString("cancel") != null) {
            return forward("/attend/manage/member/search/");
        } else if (asString("submit") != null) {

            String id = asString("id");
            if (id != null) {
                memberService.delete(id);
                requestScope("id", id);
                return forward("submit.jsp");
            } else {
                throw new RuntimeException("削除キーがないっす。");
            }
        }
        throw new RuntimeException("予期してないよ。");      
    }
}
