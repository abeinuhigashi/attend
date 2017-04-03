package slim3.controller.attend.sp.member.practice.next;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;
import slim3.service.AttendanceService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

import com.google.appengine.api.datastore.Key;

public class IndexController extends Controller {

    private static AttendanceService attendanceSvc = new AttendanceService();
    private static PracticeService practiceSvc = new PracticeService();

    @Override
    public Navigation run() throws Exception {

        Member loginUser = sessionScope(Constants.SESSION_KEY_LOGIN_USER);

        // 入力パラメータのチェック
        Key practiceKey = asKey("practiceKey");

        // 途中で処理が終了する事があるため、先にボタン制御のデフォルト値を設定するy。
        requestScope("prevButtonDisp", false);
        requestScope("nextButtonDisp", false);

        // 練習日が指定されていなかったら、現在日付からもっとも近い次の練習日を取得して表示する
        if (practiceKey == null) {
            List<Practice> nextPracticeList =
                practiceSvc.search(AttendDateUtil.getCurrentDate(), 1);
            // 練習が取得出来ない場合は処理終了。
            if (nextPracticeList == null || nextPracticeList.size() == 0) {
                return forward("index.jsp");
            }
            practiceKey = nextPracticeList.get(0).getKey();
        }

        // 前へ、次へがクリックされていたら、表示日を変更する
        if (asString("prev") != null) {
            practiceKey = practiceSvc.getPreviousPracticeKey(practiceKey, 1);
            if (practiceKey == null) {
                return forward("index.jsp");
            }
        }
        if (asString("next") != null) {
            practiceKey = practiceSvc.getNextPracticeKey(practiceKey, 1);
            if (practiceKey == null) {
                return forward("index.jsp");
            }
        }

        // １つ前の練習日が存在する場合、前へボタンを表示する。
        if (practiceSvc.getPreviousPracticeKey(practiceKey, 1) != null) {
            requestScope("prevButtonDisp", true);
        }
        // １つ先の練習日が存在する場合、次へボタンを表示する。
        if (practiceSvc.getNextPracticeKey(practiceKey, 1) != null) {
            requestScope("nextButtonDisp", true);
        }

        // 画面に表示する出席情報を取得します。
        Attendance dispAttendance =
            attendanceSvc.searchFromMemberKeyAndPracticeKey(
                loginUser.getKey(),
                practiceKey);
        // 出席情報が存在しない場合は初期化した出席情報を取得。
        if (dispAttendance == null) {
            dispAttendance =
                attendanceSvc.getInitalizedAttendance(
                    loginUser,
                    practiceSvc.searchFromKey(practiceKey));
            attendanceSvc.regist(dispAttendance);
        }
        requestScope("dispAttendance", dispAttendance);

        return forward("index.jsp");

    }
}
