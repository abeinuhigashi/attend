package slim3.controller.attend.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.ApplicationMessage;
import org.slim3.util.StringUtil;

import slim3.constants.Constants;
import slim3.model.Member;
import slim3.model.MemberAuth;
import slim3.service.MemberAuthService;
import slim3.service.MemberService;

public class LoginController extends Controller {

    private MemberService memberSvc = new MemberService();
    private MemberAuthService authSvc = new MemberAuthService();

    @Override
    public Navigation run() throws Exception {

        Member loginUser = null;

        // Cookie情報でログインを行う
        loginUser = loginFromCookieKey();

        // Cookie情報でログイン出来ない場合、RequestParameterからログインを行う。またログイン出来たらその情報をCookieに保存する。
        if (loginUser == null) {
            loginUser = loginFromReqParam();
            setLoginKeyToCookie(loginUser);
        }

        // ログイン出来ていたらその情報をセッションに保存する。
        if (loginUser != null) {
            setLoginInfoToSession(loginUser);
        } else {
            errors.put("cannotLogin", ApplicationMessage.get("login.error"));
        }

        // ログインに成功しており、遷移先の指定があればそっちに送る。
        
        // TODO
        // spの時は振り分ける必要がある。
        String fromPath =
            (String) request.getAttribute(Constants.ATTRKEY_FROM_PATH);
        if (StringUtil.isEmpty(fromPath) || loginUser == null) {
            return forward("/attend/");
        } else {
            return forward(fromPath.substring(fromPath.indexOf("/attend/")));
        }
    }

    /**
     * リクエストパラメータ（id, password）でログインを行う。
     * 
     * @return
     */
    private Member loginFromReqParam() {
        return memberSvc.login(asString("id"), asString("password"));
    }

    /**
     * クッキー情報からログインを行う。
     * 
     * @return
     */
    private Member loginFromCookieKey() {
        return memberSvc.login(getLoginKey());
    }

    /**
     * クッキーからログイン用のキーを取得する。
     * 
     * @param req
     * @return
     */
    private String getLoginKey() {
        String cookieKey = null;
        Cookie[] cookies = request.getCookies();

        // Cookieが全く存在しない場合はnullを返却
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        // Cookieの中からログイン用のキーを探す
        for (Cookie cookie : cookies) {
            if (Constants.COOKIE_KEY.equals(cookie.getName())) {
                cookieKey = cookie.getValue();
                break;
            }
        }
        return cookieKey;
    }

    /**
     * ログイン情報をセッションに保存する。
     * 
     * @param loginUser
     */
    private void setLoginInfoToSession(Member loginUser) {
        MemberAuth loginUserAuth = authSvc.searchFromMemberKey(loginUser);

        if (loginUserAuth == null) {
            // 団員の登録時に団員権限情報は作成されるため、基本的にここは通らないけど、
            // もし作成されてなかった場合は、初期値で登録する。
            loginUserAuth = authSvc.registAsInitial(loginUser);
        }

        // フィルターで権限情報を取得するため、hot-reloadingなクラスがcoolなフィルタで依存してしまわないように、hashMapに詰め替え
        Map<String, Integer> loginUserAuthMap = new HashMap<String, Integer>();
        loginUserAuthMap.put(
            Constants.MAP_KEY_ATTENDANCE,
            loginUserAuth.getAttendance());
        loginUserAuthMap.put(
            Constants.MAP_KEY_MEMBER,
            loginUserAuth.getMember());
        loginUserAuthMap.put(
            Constants.MAP_KEY_PRACTICE,
            loginUserAuth.getPractice());
        loginUserAuthMap.put(
            Constants.MAP_KEY_MEMBERAUTH,
            loginUserAuth.getMemberAuth());

        // セッションに登録
        sessionScope(Constants.SESSION_KEY_LOGIN_USER, loginUser);
        sessionScope(Constants.SESSION_KEY_LOGIN_USER_AUTH, loginUserAuthMap);
    }

    /**
     * ログイン情報をCookieに保存する。
     * 
     * @param loginUser
     */
    private void setLoginKeyToCookie(Member loginUser) {

        if (loginUser == null) {
            return;
        }

        String cookieKey = memberSvc.getLoginCookieKey(loginUser);
        Cookie cookie = new Cookie(Constants.COOKIE_KEY, cookieKey);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
