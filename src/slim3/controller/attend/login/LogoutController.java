package slim3.controller.attend.login;

import javax.servlet.http.Cookie;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;

public class LogoutController extends Controller {

    @Override
    public Navigation run() throws Exception {

        // ログイン用のクッキーを削除する
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (Constants.COOKIE_KEY.equals(cookie.getName())) {
                Cookie delCookie = new Cookie(Constants.COOKIE_KEY, null);
                delCookie.setMaxAge(60 * 60 * 24 * 365);
                delCookie.setPath("/");
                response.addCookie(delCookie);
            }
        }

        removeSessionScope(Constants.SESSION_KEY_LOGIN_USER);
        removeSessionScope(Constants.SESSION_KEY_LOGIN_USER_AUTH);

        return forward("/attend/");
    }
}
