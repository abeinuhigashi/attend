package slim3.controller.attend.mobile.menu.updateAttendance;

import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;

import slim3.model.Attendance;
import slim3.service.AttendanceService;
import slim3.util.AttendDateUtil;

import com.google.appengine.api.datastore.Key;

public class IndexController extends Controller {
    
    private final String DATE_PATTERN = "yyyyMM";
    private static AttendanceService attendanceSvc = new AttendanceService();
    
    @Override
    public Navigation run() throws Exception {
        
        // ParameterからAttendanceKeyを取得する。
        // 練習日が当日以前ならばエラー。
        // エラー時遷移先は出欠確認一覧画面。Parameterから基準日が取得できたら、その年月。なければ現在日付を基準日。
        // 問題がなければ、PracticeKeyから出欠情報と練習情報を取得して、画面に表示する。
        
        Key attendanceKey = asKey("attendanceKey");
        if(attendanceKey == null) {
            // TODO エラー専用画面に飛ばす
        }
        Attendance attendance = attendanceSvc.searchFromKey(attendanceKey);
        if(attendance == null) {
            // TODO エラー専用画面に飛ばす
        }
        Date currentDate = AttendDateUtil.getCurrentDate();
        
        // 現在日付が練習日より以前であれば登録可能
        if(currentDate.compareTo(DateUtil.clearTimePart(attendance.getPracticeRef().getModel().getStartDate())) < 0){
            // 登録可能
            request.setAttribute("attend", attendance);
        } else {
            // 登録不可能
            errors.put("cannotUpdate", "エラー：当日以前の出欠は更新できません。");
            // 出欠確認一覧画面に飛ばす
            String date = getBaseYearAndMonthStr();
            request.setAttribute("jsessionid", asString("jsessionid"));
            return forward("/attend/mobile/menu/confirmAttendance/?date=" + date);
        }
        
        requestScope("date", asString("date"));
        requestScope("jsessionid", request.getSession(false).getId());

        return forward("index.jsp");
    }
    
    /**
     * requestParameters から基準の年月を取得します requestParameters
     * に存在しない場合はシステム日付から取得します。（JST）
     * 
     * @return
     */
    private String getBaseYearAndMonthStr() {

        String yearAndDateStr = asString("date");

        if (yearAndDateStr == null) {
            yearAndDateStr =
                DateUtil
                    .toString(AttendDateUtil.getCurrentDate(), DATE_PATTERN);
        }
        return yearAndDateStr;
    }    
}
