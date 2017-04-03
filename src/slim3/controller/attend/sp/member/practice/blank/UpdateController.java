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
     * RequestParameter����Attendance���\�z����B
     * 
     * @return �\�z���ꂽAttendance
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
     * ���̓`�F�b�N���s���܂��B �����͓͂��͕s�� �x���A���ނ̎��ɂ͗��R���K�v
     * 
     * @return
     */
    private boolean checkInput() {

        Integer attendance = asInteger("attendance");

        // �����͓͂��͕s��
        if (attendance == null || attendance == Constants.ATTENDANCE_NOT_INPUT) {
            errors.put("cannotUpdate", "�����͓͂��͕s�ł����B");
            return false;
        }

        // �x�����ނ̎��ɗ��R����͂��Ă��邩
        if (attendance == Constants.ATTENDANCE_TARDINESS
            || attendance == Constants.ATTENDANCE_EARLYLEAVE) {
            String ratical = asString("racital");
            if (ratical == null || ratical.isEmpty()) {
                errors.put("cannotUpdate", "�x�����ނ̏ꍇ�ɂ͗��R�����ĂˁB");
                return false;
            }
        }

        return true;
    }

}
