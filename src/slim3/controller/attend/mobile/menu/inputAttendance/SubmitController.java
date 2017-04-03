package slim3.controller.attend.mobile.menu.inputAttendance;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.service.AttendanceService;

import com.google.appengine.api.datastore.Key;

public class SubmitController extends Controller {

    private static AttendanceService svc = new AttendanceService();

    @Override
    public Navigation run() throws Exception {
        
        // 更新情報を取得
        Key attendanceKey = asKey("attendanceKey");
        Integer attendance = asInteger("inputAttendance");
        String racital = asString("inputRacital");

        if(attendanceKey == null || attendance == null) {
            // TODO エラー画面に遷移                
        }
        
        // 遅刻、早退なのに時間が書いてない場合はエラーとして自画面遷移する
        if(!customValidate(attendance, racital)){
            requestScope("message", "エラー：遅刻、早退の場合はコメントに時間を入力してください。");
            requestScope("jsessionid", request.getSession(false).getId());
            return forward(basePath);
        }
        
        // 更新し、未入力一覧画面を表示する。更新成功メッセージを表示するが、工数削減のためerrorsを使う
        svc.update(attendanceKey, attendance, racital);
        requestScope("message", "更新しました。");
        requestScope("jsessionid", request.getSession(false).getId());

        return forward(basePath);
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
