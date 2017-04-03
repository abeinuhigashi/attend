package slim3.controller.attend.sp.member.practice.monthly;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;
import slim3.service.AttendanceService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

public class IndexController extends Controller {

    private final String DATE_PATTERN = "yyyyMM";
    private AttendanceService attendanceSvc = new AttendanceService();
    private PracticeService practiceSvc = new PracticeService();

    @Override
    public Navigation run() throws Exception {
        
        
        // ログインをしていない場合は、ログインページに遷移する。
        if(sessionScope(Constants.SESSION_KEY_LOGIN_USER) == null) {
            requestScope(Constants.ATTRKEY_FROM_PATH, request.getRequestURL());
            return forward(Constants.PATH_LOGIN_ERROR_SP);
        }
        
        String currentDateStr = getBaseYearAndMonthStr();

        if(currentDateStr == null || currentDateStr.isEmpty() || currentDateStr.equals("null")) {
            return forward("index.jsp");
        }

        int year = Integer.valueOf(currentDateStr.substring(0, 4));
        int month = Integer.valueOf(currentDateStr.substring(4, 6));

        request.setAttribute("currentYear", year);
        request.setAttribute("currentMonth", month);
        request.setAttribute("nextDate", getNextYearAndMonthStr(currentDateStr));
        request.setAttribute("currentDate", currentDateStr);
        request.setAttribute("beforeDate", getBeforeYearAndMonthStr(currentDateStr));
        request.setAttribute("attendanceList", ConstructAttendanceList(year, month));

        return forward("index.jsp");
    }
    
    /**
     * requestParameters から基準の年月を取得します
     * requestParameters に存在しない場合はシステム日付から取得します。（JST）
     *
     * @return
     */
    private String getBaseYearAndMonthStr() {

        String yearAndDateStr = asString("date");

        if(yearAndDateStr == null) {
            yearAndDateStr = DateUtil.toString(AttendDateUtil.getCurrentDate(), DATE_PATTERN);
        }
        return yearAndDateStr;

    }

    /**
     * 基準の年月から翌月の年月を取得します。
     *
     * @param baseDateStr
     * @return
     */
    private String getNextYearAndMonthStr(String baseDateStr) {
        if(baseDateStr == null) {
            return null;
        }
        return DateUtil.toString(AttendDateUtil.getFirstDayOfNextMonth(DateUtil.toDate(baseDateStr,DATE_PATTERN)), DATE_PATTERN);
    }

    /**
     * 基準の年月から先月の年月を取得します。
     *
     * @param baseDateStr
     * @return
     */
    private String getBeforeYearAndMonthStr(String baseDateStr) {
        if(baseDateStr == null) {
            return null;
        }
        return DateUtil.toString(AttendDateUtil.getFirstDayOfBeforeMonth(DateUtil.toDate(baseDateStr,DATE_PATTERN)), DATE_PATTERN);
    }

    
    /**
     * 指定された年月について、ログインユーザーの出欠リストを作成します。
     * 
     * @param year
     * @param month
     * @return
     */
    private List<Attendance> ConstructAttendanceList(int year, int month){
        Member loginUser = sessionScope(Constants.SESSION_KEY_LOGIN_USER);
        if(loginUser == null) {
            // TODO ログインしていない例外を発生させる。
        }

        // ログインユーザーと練習日のリストから、画面に表示する出欠情報を取得する。
        List<Attendance> attendanceList = null;// 練習日程が登録されていない場合の画面表示のために、ここではnull
        List<Practice> practiceList = practiceSvc.searchFromYearAndMonth(year, month);

        if(practiceList != null && practiceList.size() > 0){
            attendanceList = new ArrayList<Attendance>();
            for(Practice practice : practiceList) {
                Attendance attendance = attendanceSvc.searchFromMemberIdAndPracticeDate(loginUser.getId(), practice.getStartDate());
                if(attendance == null) {
                    attendance = attendanceSvc.getInitalizedAttendance(loginUser, practice);
                }
                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }
}