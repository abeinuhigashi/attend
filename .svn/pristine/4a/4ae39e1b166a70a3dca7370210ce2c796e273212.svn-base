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

        // 更新を行ったMemberを格納するリスト
        List<Member> updatedMemberList = new ArrayList<Member>();
        
        logger.info("Batch:JudgeNotUsedMemberController start...");
        
        // 3ヶ月前の日付を算出
        Calendar cal = Calendar.getInstance();
        cal.setTime(AttendDateUtil.getCurrentDate());
        cal.add(Calendar.MONTH, -3);
        
        // 最終ログイン日時が基準日より以前のMember（長期未入力者）を取得
        List<Member> notUsedMemberList = memberSvc.searchFromLastLoginDateTimeBeforeBaseDate(cal.getTime());

        // 長期未入力者でなければ、長期未入力者として設定する
        for(Member member : notUsedMemberList) {
            if(!member.isNotUsedLongTime()){
                member.setNotUsedLongTime(true);
                memberSvc.update(member);
                updatedMemberList.add(member);
                logger.info("Update notUsedLongTime. ID:" + member.getId() + " " + member.getLastLoginDateTime());
            }
        }
        
        // 長期未入力者が存在しなければログに出力。存在した場合は管理者にメールを送信する。
        if(updatedMemberList.size() == 0){
            System.out.println("There is no target user.");
        } else {
            sendMailToAdmin(updatedMemberList);
        }
        
        logger.info("Batch:JudgeNotUsedMemberController finished.");
        return null;
    }
    
    /**
     * 管理者にメールを送信します。
     * 
     * @param notUsedMemberList
     */
    private void sendMailToAdmin(List<Member> notUsedMemberList) {
        String subject = "長期未入力者設定のお知らせ。";
        String msg = "自動判定機能により、以下の人を長期未入力者に設定しました。 ";
        
        StringBuffer strBuf = new StringBuffer();
        for(Member member : notUsedMemberList) {
            strBuf.append(member.getLastName() + member.getFirstName() + " ");
        }
        msg = msg + strBuf.toString();
        
        mailSendSvc.sendMailToSystemAdmin(subject, msg);
    }    
}
