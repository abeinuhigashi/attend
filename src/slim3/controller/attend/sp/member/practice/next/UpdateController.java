package slim3.controller.attend.sp.member.practice.next;

import java.util.Date;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.service.AttendanceService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

public class UpdateController extends Controller {

    private static AttendanceService attendanceSvc = new AttendanceService();
    private static PracticeService practiceSvc = new PracticeService();

    @Override
    public Navigation run() throws Exception {
        if (checkInput()) {
            Attendance attendance = attendanceSvc.update(ContstructAttendance());
            requestScope("practiceKey", attendance.getPracticeKey());
            requestScope("success", true);
        }
        return forward(basePath);
    }
    
    /**
     * RequestParameterからAttendanceを構築する。
     * 
     * @return 構築されたAttendance
     */
    private Attendance ContstructAttendance() {

        Attendance attendance = attendanceSvc.searchFromKey(asKey("attendanceKey"));
        attendance.setAttendance(asInteger("attendance"));
        attendance.setRacital(asString("racital"));

        return attendance;
    }

    /**
     * その練習日が過去かどうかチェックします。 過去または当日であればtrueを返却します。
     * 
     * @return 過去または当日であればtrue
     */
    private boolean isPast() {

        Date practiceDate =
            practiceSvc.searchFromKey(asKey("practiceKey")).getStartDate();
        return AttendDateUtil.checkPast(practiceDate);
    }

    /**
     * 入力チェックを行います。 未入力は入力不可 遅刻、早退の時には理由が必要 過去日の出欠は変更不可
     * 
     * @return
     */
    private boolean checkInput() {

        Integer attendance = asInteger("attendance");

        // 未入力は入力不可
        if (attendance == null || attendance == Constants.ATTENDANCE_NOT_INPUT) {
            errors.put("cannotUpdate", "未入力は入力不可ですヨ。");
            return false;
        }

        // 遅刻早退の時に理由を入力しているか
        if (attendance == Constants.ATTENDANCE_TARDINESS
            || attendance == Constants.ATTENDANCE_EARLYLEAVE) {
            String ratical = asString("racital");
            if (ratical == null || ratical.isEmpty()) {
                errors.put("cannotUpdate", "遅刻早退の場合には理由を入れてね。");
                return false;
            }
        }

        // 出欠入力している練習が過去かどうかチェック
        if (isPast()) {
            errors.put("cannotUpdate", "当日以前の出欠の変更はマネージャーに連絡してね。");
            return false;
        }

        return true;
    }
}
