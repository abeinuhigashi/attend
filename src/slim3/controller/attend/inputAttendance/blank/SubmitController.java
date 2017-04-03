package slim3.controller.attend.inputAttendance.blank;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.service.AttendanceService;

import com.google.appengine.api.datastore.Key;

public class SubmitController extends Controller {
    
    private static AttendanceService attendanceSvc = new AttendanceService();


    @Override
    public Navigation run() throws Exception {
        
        // �X�V�����擾
        Key attendanceKey = asKey("attendanceKey");
        Integer attendance = asInteger("inputAttendance");
        String racital = asString("inputRacital");

        if(attendanceKey == null) {
            // TODO �G���[��ʂɑJ��            
        }
        
        if(attendance == null) {
            requestScope("message", "�o������͂��ĉ������B");
            return forward(basePath);            
        }
        
        // �x���A���ނȂ̂Ɏ��Ԃ������ĂȂ��ꍇ�̓G���[�Ƃ��Ď���ʑJ�ڂ���
        if(!customValidate(attendance, racital)){
            requestScope("message", "�x���A���ނ̏ꍇ�̓R�����g�Ɏ��Ԃ���͂��Ă��������B");
            return forward(basePath);
        }
        
        // �X�V���A�����͈ꗗ��ʂ�\������B�X�V�������b�Z�[�W��\�����邪�A�H���팸�̂���errors���g��
        attendanceSvc.update(attendanceKey, attendance, racital);
        requestScope("message", "�X�V���܂����B");

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
