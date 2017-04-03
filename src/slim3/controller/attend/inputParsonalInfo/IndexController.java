package slim3.controller.attend.inputParsonalInfo;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;

import slim3.constants.Constants;
import slim3.model.Member;
import slim3.service.MemberService;

public class IndexController extends Controller {

    private MemberService memberSvc = new MemberService();

    @Override
    public Navigation run() throws Exception {

        Member loginUser = sessionScope(Constants.SESSION_KEY_LOGIN_USER);

        if(loginUser == null) {
            requestScope(Constants.ATTRKEY_FROM_PATH, request.getRequestURL());
            return forward(Constants.PATH_LOGIN_ERROR);
        } else {            
            Member member = memberSvc.searchFromId(loginUser.getId());
            requestScope("key", member.getKey());
            requestScope("id", member.getId());
            requestScope("lastName", member.getLastName());
            requestScope("firstName", member.getFirstName());
            requestScope("nickName", member.getNickName());
            requestScope("birthDay", DateUtil.toString(
                member.getBirthDay(),
                DateUtil.ISO_DATE_PATTERN));
            requestScope("mailAddress", member.getMailAddress());
            requestScope("part", member.getPart());
            requestScope("mailUse", member.isMailUse());
            
            return forward("index.jsp");
        }
    }
}
