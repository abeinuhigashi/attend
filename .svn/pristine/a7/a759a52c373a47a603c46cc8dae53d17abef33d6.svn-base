package slim3.util;

import java.util.StringTokenizer;

public class PathParser {


    public static String parse(String path) {

        StringTokenizer tokens = new StringTokenizer(path, "/");
        int count = tokens.countTokens();

        StringBuffer result = new StringBuffer("/");

        boolean reserve = false;

        for (int i = 0; i < count -1; i++) {//
            //パスのmanageの次から、jspの前までを保存
            String token = tokens.nextToken();

            if (reserve) {
                result.append(token).append("/");
            }

            if (token.equals(AuthPropertyUtil.getAuthRealmBase())) {
                reserve = true;
            }

        }

        return result.toString();
    }

}
