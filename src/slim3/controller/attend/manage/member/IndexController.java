package slim3.controller.attend.manage.member;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        if (sessionScope(Constants.SESSION_KEY_LOGIN_USER) == null) {
            requestScope(Constants.ATTRKEY_FROM_PATH, request.getRequestURL());
            return forward(Constants.PATH_LOGIN_ERROR);
        }
        return forward("index.jsp");
    }
}
