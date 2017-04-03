package slim3.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slim3.controller.router.Router;
import org.slim3.controller.router.RouterFactory;
import org.slim3.util.RequestUtil;

import slim3.constants.Constants;
import slim3.util.SettingPropertyUtil;

public class LoginFilter implements Filter {

    protected ServletContext servletContext;
    private final String IS_LOGIN_FORWARDED = "isLoginForwarded";

    public void init(FilterConfig config) throws ServletException {
        servletContext = config.getServletContext();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        doFilter(
            (HttpServletRequest) request,
            (HttpServletResponse) response,
            chain);
    }

    protected void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = RequestUtil.getPath(request);

        // ログイン遷移が不要なパスについてはログイン処理を行わない
        if (!isNeedLogin(path)) {
            chain.doFilter(request, response);
            return;
        }

        // ユーザー情報がセッションに存在しない場合、ログインを行う（login controllerに飛ばす）
        HttpSession session = request.getSession(false);
        Object userInfo = null;
        if (session != null) {
            userInfo = session.getAttribute(Constants.SESSION_KEY_LOGIN_USER);
        }

        if (userInfo == null && request.getAttribute(IS_LOGIN_FORWARDED) == null) {
            // ログイン処理後の戻り遷移先を記録
            request.setAttribute(Constants.ATTRKEY_FROM_PATH, request
                .getRequestURL()
                .toString());
            request.setAttribute(IS_LOGIN_FORWARDED, "true");

            // ログイン処理にフォワード
            servletContext
                .getRequestDispatcher(Constants.PATH_LOGIN_CONTROLLER)
                .forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * Requestされたパスがログイン必要かどうか判定する。
     * 
     * @param path
     * @return
     */
    private boolean isNeedLogin(String path) {

        // Staticなファイルについては処理を行わない。jspとか。
        Router router = RouterFactory.getRouter();
        if (router.isStatic(path)) {
            return false;
        }

        // バッチの場合はログインは不要
        String rootPath = SettingPropertyUtil.getProperty("rootpath.batch");
        if (path.startsWith(rootPath)) {
            return false;
        }

        // ログインのパスの場合はログインは不要（ここを回避しないと無限ループ）
        if (Constants.PATH_LOGIN_CONTROLLER.equals(path)) {
            return false;
        }
        
        // トップの場合もログインチェック不要
        if ((Constants.PATH_ROOT+"/").equals(path)) {
            return false;
        }        

        return true;
    }
}
