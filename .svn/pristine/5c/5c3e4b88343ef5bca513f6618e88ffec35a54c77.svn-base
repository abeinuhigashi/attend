package slim3.controller.attend.mobile;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.model.Member;
import slim3.service.MemberService;

public class LoginController extends Controller {

    private MemberService memberSvc = new MemberService();
    
    @Override
    public Navigation run() throws Exception {

        String id = asString("id");
        String password = asString("password");
        
        if(id == null || password == null) {
            errors.put("loginInputError", "ID、パスワードを入力してください。");
            return forward("index.jsp");
        }
        
        Member member = memberSvc.login(id, password);
        
        if(member != null) {
            sessionScope(Constants.SESSION_KEY_LOGIN_USER, member);
            requestScope("jsessionid", request.getSession(false).getId());

            String fromPath = (String) request.getAttribute(Constants.ATTRKEY_FROM_PATH);
            if(fromPath == null || fromPath.isEmpty()){
                return forward("/attend/mobile/menu/");
            } else {
                return forward(fromPath.substring(fromPath.indexOf("/attend/"), fromPath.indexOf(";jsessionid")));
            }
        } else {
            errors.put("loginInputError", "IDまたはパスワードが違います。");
            return forward("index.jsp");
        }
    }
}
