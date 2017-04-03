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
        
        // �X�V�����擾
        Key attendanceKey = asKey("attendanceKey");
        Integer attendance = asInteger("inputAttendance");
        String racital = asString("inputRacital");

        if(attendanceKey == null || attendance == null) {
            // TODO �G���[��ʂɑJ��                
        }
        
        // �x���A���ނȂ̂Ɏ��Ԃ������ĂȂ��ꍇ�̓G���[�Ƃ��Ď���ʑJ�ڂ���
        if(!customValidate(attendance, racital)){
            requestScope("message", "�G���[�F�x���A���ނ̏ꍇ�̓R�����g�Ɏ��Ԃ���͂��Ă��������B");
            requestScope("jsessionid", request.getSession(false).getId());
            return forward(basePath);
        }
        
        // �X�V���A�����͈ꗗ��ʂ�\������B�X�V�������b�Z�[�W��\�����邪�A�H���팸�̂���errors���g��
        svc.update(attendanceKey, attendance, racital);
        requestScope("message", "�X�V���܂����B");
        requestScope("jsessionid", request.getSession(false).getId());

        return forward(basePath);
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
