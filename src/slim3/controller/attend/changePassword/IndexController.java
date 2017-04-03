package slim3.controller.attend.changePassword;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.model.Member;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {

        Member loginUser = sessionScope(Constants.SESSION_KEY_LOGIN_USER);

        if (loginUser == null) {
            requestScope(Constants.ATTRKEY_FROM_PATH, request.getRequestURL());
            return forward(Constants.PATH_LOGIN_ERROR);
        } else {
            requestScope("key", loginUser.getKey());
            return forward("index.jsp");
        }
    }
}
