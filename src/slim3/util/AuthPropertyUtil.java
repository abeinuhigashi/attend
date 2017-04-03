package slim3.util;

public class AuthPropertyUtil {

    private static String getProperty(String key){
        return SettingPropertyUtil.getProperty(key);
    }

    public static boolean isAuthRealm(String path) {

        String authRealmBase =
            "/attend/" + AuthPropertyUtil.getAuthRealmBase() + "/";
        if (path.startsWith(authRealmBase)) {
            return true;
        }

        return false;
    }

    public static boolean isAttendanceReferRealm(String path) {

        String parsedPath = PathParser.parse(path);

        String[] attendanceReferRealm = getAttendanceReferRealms();

        if (attendanceReferRealm != null) {
            for (String referPath : attendanceReferRealm) {
                if (parsedPath.startsWith(referPath)
                    && !isAttendanceUpdateRealm(path)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isAttendanceUpdateRealm(String path) {

        String parsedPath = PathParser.parse(path);

        String[] attendanceUpdateRealm = getAttendanceUpdateRealm();

        if (attendanceUpdateRealm != null) {
            for (String referPath : attendanceUpdateRealm) {
                if (parsedPath.startsWith(referPath)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isPracticeReferRealm(String path) {

        String parsedPath = PathParser.parse(path);

        String[] practiceReferRealm = getPracticeReferRealm();

        if (practiceReferRealm != null) {
            for (String referPath : practiceReferRealm) {
                if (parsedPath.startsWith(referPath)
                    && !isPracticeUpdateRealm(path)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isPracticeUpdateRealm(String path) {

        String parsedPath = PathParser.parse(path);

        String[] practiceUpdateRealm = getPracticeUpdateRealm();

        if (practiceUpdateRealm != null) {
            for (String referPath : practiceUpdateRealm) {
                if (parsedPath.startsWith(referPath)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isMemberReferRealm(String path) {

        String parsedPath = PathParser.parse(path);

        String[] memberReferRealm = getMemberReferRealm();

        if (memberReferRealm != null) {
            for (String referPath : memberReferRealm) {
                if (parsedPath.startsWith(referPath)
                    && !isMemberUpdateRealm(path)
                    && !isMemberAuthUpdateRealm(path)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isMemberUpdateRealm(String path) {

        String parsedPath = PathParser.parse(path);

        String[] memberUpdateRealm = getMemberUpdateRealm();

        if (memberUpdateRealm != null) {
            for (String referPath : memberUpdateRealm) {
                if (parsedPath.startsWith(referPath)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 団員情報参照権限は存在しないため、コメントアウト
    // public static boolean isMemberAuthReferRealm(String path) {
    //
    // String parsedPath = PathParser.parse(path);
    //
    // String[] memberAuthReferRealm = getMemberAuthReferRealm();
    //
    // if (memberAuthReferRealm != null) {
    // for (String referPath : memberAuthReferRealm) {
    // if (parsedPath.startsWith(referPath)
    // && !isMemberAuthUpdateRealm(path)) {
    // return true;
    // }
    // }
    // }
    //
    // return false;
    // }

    public static boolean isMemberAuthUpdateRealm(String path) {

        String parsedPath = PathParser.parse(path);

        String[] memberAuthUpdateRealm = getMemberAuthUpdateRealm();

        if (memberAuthUpdateRealm != null) {
            for (String referPath : memberAuthUpdateRealm) {
                if (parsedPath.startsWith(referPath)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static String[] getAttendanceReferRealms() {
        return getProperty("attendance.refer").split(",");
    }

    private static String[] getAttendanceUpdateRealm() {
        return getProperty("attendance.update").split(",");
    }

    private static String[] getPracticeReferRealm() {
        return getProperty("practice.refer").split(",");
    }

    private static String[] getPracticeUpdateRealm() {
        return getProperty("practice.update").split(",");
    }

    private static String[] getMemberReferRealm() {
        return getProperty("member.refer").split(",");
    }

    private static String[] getMemberUpdateRealm() {
        return getProperty("member.update").split(",");
    }

    // private static String[] getMemberAuthReferRealm() {
    // return getProperty("memberAuth.refer").split(",");
    // }

    private static String[] getMemberAuthUpdateRealm() {
        return getProperty("memberAuth.update").split(",");
    }

    public static String getAuthRealmBase() {
        return getProperty("auth_realm_base");
    }

    /**
     * 未実装
     *
     * @return
     */
    @Deprecated
    public static String[] getMailReferRealm() {
        return getProperty("memberAuth.refer").split(",");
    }

    /**
     * 未実装
     *
     * @return
     */
    @Deprecated
    public static String[] getMailUpdateRealm() {
        return getProperty("memberAuth.update").split(",");
    }
}
