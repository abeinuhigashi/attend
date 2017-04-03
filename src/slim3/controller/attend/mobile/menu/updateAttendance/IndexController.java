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
        
        // Parameter����AttendanceKey���擾����B
        // ���K���������ȑO�Ȃ�΃G���[�B
        // �G���[���J�ڐ�͏o���m�F�ꗗ��ʁBParameter���������擾�ł�����A���̔N���B�Ȃ���Ό��ݓ��t������B
        // ��肪�Ȃ���΁APracticeKey����o�����Ɨ��K�����擾���āA��ʂɕ\������B
        
        Key attendanceKey = asKey("attendanceKey");
        if(attendanceKey == null) {
            // TODO �G���[��p��ʂɔ�΂�
        }
        Attendance attendance = attendanceSvc.searchFromKey(attendanceKey);
        if(attendance == null) {
            // TODO �G���[��p��ʂɔ�΂�
        }
        Date currentDate = AttendDateUtil.getCurrentDate();
        
        // ���ݓ��t�����K�����ȑO�ł���Γo�^�\
        if(currentDate.compareTo(DateUtil.clearTimePart(attendance.getPracticeRef().getModel().getStartDate())) < 0){
            // �o�^�\
            request.setAttribute("attend", attendance);
        } else {
            // �o�^�s�\
            errors.put("cannotUpdate", "�G���[�F�����ȑO�̏o���͍X�V�ł��܂���B");
            // �o���m�F�ꗗ��ʂɔ�΂�
            String date = getBaseYearAndMonthStr();
            request.setAttribute("jsessionid", asString("jsessionid"));
            return forward("/attend/mobile/menu/confirmAttendance/?date=" + date);
        }
        
        requestScope("date", asString("date"));
        requestScope("jsessionid", request.getSession(false).getId());

        return forward("index.jsp");
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
}
