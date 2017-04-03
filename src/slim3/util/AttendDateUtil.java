package slim3.util;

import java.util.Calendar;
import java.util.Date;

public class AttendDateUtil {

    /**
     * 日付がシステム日付より過去かどうか判定します。 過去であればtrue, 未来であればfalse, 当日はtrue（過去扱い）
     * 
     * @param targetDate
     * @return
     */
    public static boolean checkPast(Date targetDate) {

        Date practiceDate = targetDate;
        Calendar practiceCalendar = Calendar.getInstance();
        practiceCalendar.setTimeInMillis(practiceDate.getTime());

        int practiceYear = practiceCalendar.get(Calendar.YEAR);
        int practiceMonth = practiceCalendar.get(Calendar.MONTH);
        int practiceDay = practiceCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();

        int sysYear = calendar.get(Calendar.YEAR);
        int sysMonth = calendar.get(Calendar.MONTH);
        int sysDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (practiceYear < sysYear) {
            // 対象日の年度が過去の場合
            return true;
        } else if (practiceYear > sysYear) {
            // 対象日の年度が未来の場合
            return false;
        }

        // 以下、対象日の年度がシステム日付の年度と同じ場合
        if (practiceMonth < sysMonth) {
            // 対象日の月が過去の場合
            return true;
        } else if (practiceMonth > sysMonth) {
            // 対象日の月が未来の場合
            return false;
        }

        // 以下、対象日の月がシステム日付の月と同じ場合
        if (practiceDay <= sysDay) {
            // 当日の場合も過去に含む
            return true;
        }

        return false;
    }

    /**
     * 現在日時を取得します。（JST）
     * 
     * @return 現在日時
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 9);
        return cal.getTime();
    }

    /**
     * 翌月の１日を取得します。
     * 
     * @param baseDate
     * @return
     */
    public static Date getFirstDayOfNextMonth(Date baseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * 前月の１日を取得します。
     * 
     * @param baseDate
     * @return
     */
    public static Date getFirstDayOfBeforeMonth(Date baseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 翌々月の１日を取得します
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfTwiceNextMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 2); // 翌々月
        cal.set(Calendar.DAY_OF_MONTH, 1); // 翌々月の１日
        cal.add(Calendar.DAY_OF_MONTH, -1); // 前日

        return cal.getTime();
    }

    /**
     * 日単位で加算します。
     * 
     * @param date
     *            加算対象日付
     * @param num
     *            加算日数（マイナスなら過去日へ）
     * @return
     */
    public static Date addDayOfMonth(Date date, int num) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, num);

        return cal.getTime();
    }

    /**
     * 月単位で加算します。
     * 
     * @param date
     * @param num
     * @return
     */
    public static Date addMonth(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);

        return cal.getTime();
    }
    
    /**
     * 月単位で加算します。
     * 
     * @param date
     * @param num
     * @return
     */
    public static Date addDay(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, num);

        return cal.getTime();
    }
}
