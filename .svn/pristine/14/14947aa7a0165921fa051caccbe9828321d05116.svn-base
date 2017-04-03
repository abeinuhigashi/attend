package slim3.controller.attend.mobile.menu.updateAttendance;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.service.AttendanceService;
import slim3.util.AttendDateUtil;

import com.google.appengine.api.datastore.Key;

public class SubmitController extends Controller {
    
    private final String DATE_PATTERN = "yyyyMM";
    private static AttendanceService svc = new AttendanceService();

    @Override
    public Navigation run() throws Exception {
        // 戻るボタンが押された場合は、出欠確認一覧画面に戻る
        if(asString("back") != null) {
            String date = getBaseYearAndMonthStr();
            request.setAttribute("jsessionid", asString("jsessionid"));
            return forward("/attend/mobile/menu/confirmAttendance/?date=" + date);
        } else {
            // 更新情報を取得
            Key attendanceKey = asKey("attendanceKey");
            Integer attendance = asInteger("inputAttendance");
            String racital = asString("inputRacital");

            if(attendanceKey == null || attendance == null) {
                // TODO エラー画面に遷移                
            }
            
            // 遅刻、早退なのに時間が書いてない場合はエラーとして自画面遷移する
            if(!customValidate(attendance, racital)){
                errors.put("inputError", "エラー：遅刻、早退の場合はコメントに時間を入力してください。");
                Attendance updateAttendance = svc.searchFromKey(attendanceKey);
                
                updateAttendance.setAttendance(attendance);
                updateAttendance.setRacital(racital);
                
                requestScope("attend", updateAttendance);
                requestScope("date", asString("date"));
                requestScope("jsessionid", request.getSession(false).getId());
                
                return forward("index.jsp");
            }
            
            // 更新し、結果をrequestScopeに格納
            Attendance updatedAttendance = svc.update(attendanceKey, attendance, racital);
            requestScope("attend", updatedAttendance);
            requestScope("jsessionid", request.getSession(false).getId());

            return forward("submit.jsp");
        }
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
    
    /**
     * カスタム入力チェック
     * チェック内容：遅刻、早退と入力しているのに、コメントが空だった場合はNG
     * 
     * @param attendance
     * @param ratical
     * @return
     */
    private boolean customValidate(int attendance, String ratical) {
        
        if(attendance == Constants.ATTENDANCE_TARDINESS || attendance == Constants.ATTENDANCE_EARLYLEAVE) {
            if(ratical == null || ratical.isEmpty()) {
                return false;
            }
        }
        
        return true;
    }
}
