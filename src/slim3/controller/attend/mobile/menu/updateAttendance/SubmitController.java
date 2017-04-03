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
        // �߂�{�^���������ꂽ�ꍇ�́A�o���m�F�ꗗ��ʂɖ߂�
        if(asString("back") != null) {
            String date = getBaseYearAndMonthStr();
            request.setAttribute("jsessionid", asString("jsessionid"));
            return forward("/attend/mobile/menu/confirmAttendance/?date=" + date);
        } else {
            // �X�V�����擾
            Key attendanceKey = asKey("attendanceKey");
            Integer attendance = asInteger("inputAttendance");
            String racital = asString("inputRacital");

            if(attendanceKey == null || attendance == null) {
                // TODO �G���[��ʂɑJ��                
            }
            
            // �x���A���ނȂ̂Ɏ��Ԃ������ĂȂ��ꍇ�̓G���[�Ƃ��Ď���ʑJ�ڂ���
            if(!customValidate(attendance, racital)){
                errors.put("inputError", "�G���[�F�x���A���ނ̏ꍇ�̓R�����g�Ɏ��Ԃ���͂��Ă��������B");
                Attendance updateAttendance = svc.searchFromKey(attendanceKey);
                
                updateAttendance.setAttendance(attendance);
                updateAttendance.setRacital(racital);
                
                requestScope("attend", updateAttendance);
                requestScope("date", asString("date"));
                requestScope("jsessionid", request.getSession(false).getId());
                
                return forward("index.jsp");
            }
            
            // �X�V���A���ʂ�requestScope�Ɋi�[
            Attendance updatedAttendance = svc.update(attendanceKey, attendance, racital);
            requestScope("attend", updatedAttendance);
            requestScope("jsessionid", request.getSession(false).getId());

            return forward("submit.jsp");
        }
    }
    
    /**
     * requestParameters �����̔N�����擾���܂� requestParameters
     * �ɑ��݂��Ȃ��ꍇ�̓V�X�e�����t����擾���܂��B�iJST�j
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
     * �J�X�^�����̓`�F�b�N
     * �`�F�b�N���e�F�x���A���ނƓ��͂��Ă���̂ɁA�R�����g���󂾂����ꍇ��NG
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
