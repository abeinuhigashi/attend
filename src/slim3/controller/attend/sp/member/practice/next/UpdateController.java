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
     * RequestParameter����Attendance���\�z����B
     * 
     * @return �\�z���ꂽAttendance
     */
    private Attendance ContstructAttendance() {

        Attendance attendance = attendanceSvc.searchFromKey(asKey("attendanceKey"));
        attendance.setAttendance(asInteger("attendance"));
        attendance.setRacital(asString("racital"));

        return attendance;
    }

    /**
     * ���̗��K�����ߋ����ǂ����`�F�b�N���܂��B �ߋ��܂��͓����ł����true��ԋp���܂��B
     * 
     * @return �ߋ��܂��͓����ł����true
     */
    private boolean isPast() {

        Date practiceDate =
            practiceSvc.searchFromKey(asKey("practiceKey")).getStartDate();
        return AttendDateUtil.checkPast(practiceDate);
    }

    /**
     * ���̓`�F�b�N���s���܂��B �����͓͂��͕s�� �x���A���ނ̎��ɂ͗��R���K�v �ߋ����̏o���͕ύX�s��
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

        // �o�����͂��Ă�����K���ߋ����ǂ����`�F�b�N
        if (isPast()) {
            errors.put("cannotUpdate", "�����ȑO�̏o���̕ύX�̓}�l�[�W���[�ɘA�����ĂˁB");
            return false;
        }

        return true;
    }
}
