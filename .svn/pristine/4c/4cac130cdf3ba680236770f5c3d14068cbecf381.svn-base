package slim3.controller.attend.sp.member.practice.blank;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.service.AttendanceService;

public class UpdateController extends Controller {

    private static AttendanceService attendanceSvc = new AttendanceService();

    @Override
    public Navigation run() throws Exception {

        if (checkInput()) {
            attendanceSvc.regist(ContstructAttendance());
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

        Attendance attendance = new Attendance();
        attendance.setAttendance(asInteger("attendance"));
        attendance.setRacital(asString("racital"));
        attendance.setMemberKey(asKey("memberKey"));
        attendance.setPracticeKey(asKey("practiceKey"));

        return attendance;
    }

    /**
     * 入力チェックを行います。 未入力は入力不可 遅刻、早退の時には理由が必要
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

        return true;
    }

}
