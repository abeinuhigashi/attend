package slim3.controller.attend.mobile.menu;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;

public class IndexController extends Controller {
    
    @Override
    public Navigation run() throws Exception {

        // ���O�C������
        if(sessionScope(Constants.SESSION_KEY_LOGIN_USER) == null) {
            errors.put("notLoginError", "���O�C�������Ă��Ȃ����A���Ԃ������Ă��܂������߃��O�A�E�g����܂����B���O�C�����Ă��������B");
            return forward("/attend/mobile/");
        }

        requestScope("jsessionid", request.getSession(false).getId());
        return forward("index.jsp");        
    }
}
