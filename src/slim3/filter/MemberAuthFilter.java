package slim3.filter;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import slim3.constants.Constants;
import slim3.util.AuthPropertyUtil;

public class MemberAuthFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        if (req instanceof HttpServletRequest) {
            HttpServletRequest httpReq = (HttpServletRequest) req;
            HttpServletResponse httpRes = (HttpServletResponse) res;

            Object isRedirectObj = httpReq.getSession().getAttribute("isRedirect");

            if (isRedirectObj !=null && isRedirectObj.equals("false")) {
                httpReq.getSession().setAttribute("autherror", "");
            }

            String path = httpReq.getServletPath();

            if (checkBasic(httpReq, httpRes, path, chain)) {
                // 基本チェックがtrueの場合ここで終了
                httpReq.getSession().setAttribute("isRedirect", "false");
                chain.doFilter(httpReq, res);
                return;
            }

            // 権限チェック
            if (!checkMemberAuthAndDispatch(httpReq, httpRes, path)) {
                // 権限無しの場合はトップ画面へforwardをセット
                httpReq.getSession().setAttribute("isRedirect", "true");
                httpReq.getSession().setAttribute("autherror", "権限がありません。");
                redirectTop(httpReq, httpRes);
            }

            chain.doFilter(req, res);

        } else {
            throw new IllegalAccessError("権限情報が参照できないためエラー");
        }

    }

    @SuppressWarnings("unchecked")
    private boolean checkBasic(HttpServletRequest httpReq,
            HttpServletResponse res, String path, FilterChain chain)
            throws IOException, ServletException {

        if (path.startsWith("/_ah")) {
            // TODO 管理ページは本番だとどうする？
            return true;
        }

        HttpSession session = httpReq.getSession();
        if (session.getAttribute(Constants.SESSION_KEY_LOGIN_USER) == null) {
            // ログインしてない場合は何もしない。ログイン判定は各コントローラで。
            return true;
        }

        if (!AuthPropertyUtil.isAuthRealm(path)) {
            return true;
        }

        // ログインしている状態で、権限情報がセッションにな場合はエラー
        Map<String, Integer> auth =
            (Map<String, Integer>) session
                .getAttribute(Constants.SESSION_KEY_LOGIN_USER_AUTH);

        if (auth == null) {
            throw new IllegalAccessError("権限情報が参照できないためエラー");
        }

        return false;

    }

    private boolean checkMemberAuthAndDispatch(HttpServletRequest httpReq,
            HttpServletResponse res, String path) throws ServletException,
            IOException {

        HttpSession session = httpReq.getSession();

        @SuppressWarnings("unchecked")
        Map<String, Integer> auth =
            (Map<String, Integer>) session
                .getAttribute(Constants.SESSION_KEY_LOGIN_USER_AUTH);

        int attendanceAuth = auth.get(Constants.MAP_KEY_ATTENDANCE);
        int practiceAuth = auth.get(Constants.MAP_KEY_PRACTICE);
        int memberAuth = auth.get(Constants.MAP_KEY_MEMBER);
        int memberAuthAuth = auth.get(Constants.MAP_KEY_MEMBERAUTH);

        // 出席情報参照権限範囲
        if (AuthPropertyUtil.isAttendanceReferRealm(path)) {
            if (attendanceAuth == Constants.AUTH_REFER
                || attendanceAuth == Constants.AUTH_UPDATE) {
                return true;
            }
        }

        // 出席情報更新権限範囲
        if (AuthPropertyUtil.isAttendanceUpdateRealm(path)) {
            if (attendanceAuth == Constants.AUTH_UPDATE) {
                return true;
            }
        }

        // 練習情報参照権限範囲
        if (AuthPropertyUtil.isPracticeReferRealm(path)) {
            if (practiceAuth == Constants.AUTH_REFER
                || practiceAuth == Constants.AUTH_UPDATE) {
                return true;
            }
        }

        // 練習情報更新権限範囲
        if (AuthPropertyUtil.isPracticeUpdateRealm(path)) {
            if (practiceAuth == Constants.AUTH_UPDATE) {
                return true;
            }
        }

        // 団員情報参照権限範囲
        if (AuthPropertyUtil.isMemberReferRealm(path)) {
            if (memberAuth == Constants.AUTH_REFER
                || memberAuth == Constants.AUTH_UPDATE) {
                return true;
            }
        }

        // 団員情報更新権限範囲
        if (AuthPropertyUtil.isMemberUpdateRealm(path)) {
            if (memberAuth == Constants.AUTH_UPDATE) {
                return true;
            }
        }

        // //団員権限情報参照権限範囲
        // if(AuthPropertyUtil.isMemberAuthReferRealm(path)){
        // if(memberAuthAuth == Constants.AUTH_REFER || memberAuth ==
        // Constants.AUTH_UPDATE){
        // return true;
        // }
        // }

        // 団員権限情報更新権限範囲
        if (AuthPropertyUtil.isMemberAuthUpdateRealm(path)) {
            if (memberAuthAuth == Constants.AUTH_UPDATE) {
                return true;
            }
        }

        return false;

    }

    private void redirectTop(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String regex = ".+://.+/attend/";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(req.getRequestURL());
        if (m.find()) {
            String group = m.group();
            String top = group;
            res.sendRedirect(top);
        }

        return;
    }

}
