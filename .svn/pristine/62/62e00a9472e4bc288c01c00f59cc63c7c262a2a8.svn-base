package slim3.controller.attend.batch.reminder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;

import slim3.model.Member;
import slim3.service.MailSendService;
import slim3.service.MemberService;
import slim3.util.AttendDateUtil;

public class MemberBirthdayController extends Controller {

    private static MemberService memberSvc = new MemberService();
    private static MailSendService mailSendSvc = new MailSendService();
    private static String PATTERN_MMDD = "MMdd";

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Navigation run() throws Exception {

        logger.info(this.getClass().getName()
            + " start... at "
            + AttendDateUtil.getCurrentDate());

        // requestより当日日付を取得。もし指定されていない場合は、システム日付を取得する。
        Date today = asDate("date", DateUtil.ISO_DATE_PATTERN);
        if (today == null) {
            today = AttendDateUtil.getCurrentDate();
        }

        // 当日が誕生日のメンバー一覧を取得する。
        List<Member> memberList = getSendMemberList(today);

        // メール送信処理を行う。
        if (memberList != null && memberList.size() > 0) {
            sendMail(memberList);
            logger.info("メールを送信しました。送信団員数：" + memberList.size());
        } else {
            logger.info("誕生日の団員がいないためメールを送信しませんでした。");
        }

        return null;
    }

    /**
     * メールを送信します。
     * 
     * @param practiceList
     */
    private void sendMail(List<Member> memberList) {
        String subject = makeSubject();
        String body = makeBody(memberList);
        mailSendSvc.sendMailToUserAdmin(subject, body);
    }

    /**
     * メールタイトルを作成します。
     * 
     * @return
     */
    private String makeSubject() {
        return "本日誕生日の団員がいます。";
    }

    /**
     * メール本文を作成します。
     * 
     * @param memberList
     * @return
     */
    private String makeBody(List<Member> memberList) {
        String headerStr = "本日誕生日の団員がいます。必要ならば誕生日MLを送信して下さい。\n\n";
        String contentStr = makeContent(memberList);
        String footerStr =
            "\nこのメールはMEKAZAWAより自動配信されています。何かおかしな点があればマネージャに連絡下さい。";

        return headerStr + contentStr + footerStr;
    }

    /**
     * 団員情報から文面を作成します。
     * 
     * @param memberList
     * @return
     */
    private String makeContent(List<Member> memberList) {

        StringBuffer bufStr = new StringBuffer();

        for (Member member : memberList) {
            bufStr.append("名前："
                + member.getLastName()
                + " "
                + member.getFirstName()
                + "   誕生日: "
                + DateUtil.toString(
                    member.getBirthDay(),
                    DateUtil.ISO_DATE_PATTERN) + "\n");
        }

        return bufStr.toString();
    }

    /**
     * 当日が誕生日である人のリストを抽出します。
     * 
     * @param today
     * @return 誕生日が当日であるメンバーのリスト
     */
    private List<Member> getSendMemberList(Date today) {

        List<Member> rtnList = new ArrayList<Member>();

        String todayStr = DateUtil.toString(today, PATTERN_MMDD);
        List<Member> memberList = memberSvc.getAll();

        for (Member member : memberList) {

            // 誕生日が登録されていない場合は送信判定の対象外とする。
            Date birthDay = member.getBirthDay();
            if (birthDay == null) {
                continue;
            }

            String birthdayStr = DateUtil.toString(birthDay, PATTERN_MMDD);

            if (birthdayStr.equals(todayStr)) {
                rtnList.add(member);
            }
        }

        return rtnList;
    }
}
