package slim3.controller.attend.sp.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.ApplicationMessage;

import slim3.constants.Constants;
import slim3.model.Member;
import slim3.model.MemberAuth;
import slim3.service.MemberAuthService;
import slim3.service.MemberService;

public class LoginController extends Controller {

    private MemberService svc = new MemberService();
    private MemberAuthService authSvc = new MemberAuthService();

    @Override
    public Navigation run() throws Exception {

        Member member = svc.login(asString("id"), asString("password"));

        if (member != null) {
            MemberAuth memberAuth = authSvc.searchFromMemberKey(member);

            if (memberAuth == null) {
                // 団員の登録時に団員権限情報は作成されるため、基本的にここは通らないけど、
                // もし作成されてなかった場合は、初期値で登録してしまう。(ログインできてるから良いよね？)
                memberAuth = authSvc.registAsInitial(member);
            }

            // フィルターで権限情報を取得するため、hot-reloadingなクラスがcoolなフィルタで依存してしまわないように、
            // hashMapに詰め替え
            Map<String, Integer> authMap = new HashMap<String, Integer>();
            authMap.put(
                Constants.MAP_KEY_ATTENDANCE,
                memberAuth.getAttendance());
            authMap.put(Constants.MAP_KEY_MEMBER, memberAuth.getMember());
            authMap.put(Constants.MAP_KEY_PRACTICE, memberAuth.getPractice());
            authMap.put(
                Constants.MAP_KEY_MEMBERAUTH,
                memberAuth.getMemberAuth());

            sessionScope(Constants.SESSION_KEY_LOGIN_USER, member);
            sessionScope(Constants.SESSION_KEY_LOGIN_USER_AUTH, authMap);

            String cookieKey = svc.getLoginCookieKey(member);
            Cookie cookie = new Cookie(Constants.COOKIE_KEY, cookieKey);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);

        } else {
            errors.put("cannotLogin", ApplicationMessage.get("login.error"));
            return forward("/attend/sp/");
        }

        return forward("/attend/sp/member/practice/monthly");
    }
}
