package slim3.controller.attend.batch.maintenance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.model.Member;
import slim3.service.MailSendService;
import slim3.service.MemberService;
import slim3.util.AttendDateUtil;

public class JudgeNotUsedMemberController extends Controller {

    private MemberService memberSvc = new MemberService();
    private MailSendService mailSendSvc = new MailSendService();
    private Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Navigation run() throws Exception {

        // �X�V���s����Member���i�[���郊�X�g
        List<Member> updatedMemberList = new ArrayList<Member>();
        
        logger.info("Batch:JudgeNotUsedMemberController start...");
        
        // 3�����O�̓��t���Z�o
        Calendar cal = Calendar.getInstance();
        cal.setTime(AttendDateUtil.getCurrentDate());
        cal.add(Calendar.MONTH, -3);
        
        // �ŏI���O�C��������������ȑO��Member�i���������͎ҁj���擾
        List<Member> notUsedMemberList = memberSvc.searchFromLastLoginDateTimeBeforeBaseDate(cal.getTime());

        // ���������͎҂łȂ���΁A���������͎҂Ƃ��Đݒ肷��
        for(Member member : notUsedMemberList) {
            if(!member.isNotUsedLongTime()){
                member.setNotUsedLongTime(true);
                memberSvc.update(member);
                updatedMemberList.add(member);
                logger.info("Update notUsedLongTime. ID:" + member.getId() + " " + member.getLastLoginDateTime());
            }
        }
        
        // ���������͎҂����݂��Ȃ���΃��O�ɏo�́B���݂����ꍇ�͊Ǘ��҂Ƀ��[���𑗐M����B
        if(updatedMemberList.size() == 0){
            System.out.println("There is no target user.");
        } else {
            sendMailToAdmin(updatedMemberList);
        }
        
        logger.info("Batch:JudgeNotUsedMemberController finished.");
        return null;
    }
    
    /**
     * �Ǘ��҂Ƀ��[���𑗐M���܂��B
     * 
     * @param notUsedMemberList
     */
    private void sendMailToAdmin(List<Member> notUsedMemberList) {
        String subject = "���������͎Ґݒ�̂��m�点�B";
        String msg = "��������@�\�ɂ��A�ȉ��̐l�𒷊������͎҂ɐݒ肵�܂����B ";
        
        StringBuffer strBuf = new StringBuffer();
        for(Member member : notUsedMemberList) {
            strBuf.append(member.getLastName() + member.getFirstName() + " ");
        }
        msg = msg + strBuf.toString();
        
        mailSendSvc.sendMailToSystemAdmin(subject, msg);
    }    
}
