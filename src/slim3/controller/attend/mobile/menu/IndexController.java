package slim3.controller.attend.mobile.menu;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;

public class IndexController extends Controller {
    
    @Override
    public Navigation run() throws Exception {

        // ログイン判定
        if(sessionScope(Constants.SESSION_KEY_LOGIN_USER) == null) {
            errors.put("notLoginError", "ログインをしていないか、時間がたってしまったためログアウトされました。ログインしてください。");
            return forward("/attend/mobile/");
        }

        requestScope("jsessionid", request.getSession(false).getId());
        return forward("index.jsp");        
    }
}
