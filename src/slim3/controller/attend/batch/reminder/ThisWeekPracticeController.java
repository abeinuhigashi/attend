package slim3.controller.attend.batch.reminder;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;
import org.slim3.util.StringUtil;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;
import slim3.service.AttendanceService;
import slim3.service.MailSendService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

public class ThisWeekPracticeController extends Controller {

    private static PracticeService practiceSvc = new PracticeService();
    private static AttendanceService attendanceSvc = new AttendanceService();
    private static MailSendService mailSendSvc = new MailSendService();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Navigation run() throws Exception {

        logger.info(this.getClass().getName()
            + " start... at "
            + AttendDateUtil.getCurrentDate());

        // 本日練習があるかどうか確認する
        List<Practice> practiceList = getThisWeekPracticeList();
        
        // 練習がある場合、メールを作成し送信する。
        if (practiceList != null && practiceList.size() > 0) {
            sendMail(practiceList);
            logger.info("メールを送信しました。対象練習数：" + practiceList.size());
        } else {
            logger.info("該当する練習がないためメールを送信しませんでした。");
        }

        return null;
    }

    /**
     * メール送信が必要な練習リストを取得します。期間：７日後まで。
     * 
     * @return
     */
    private List<Practice> getThisWeekPracticeList() {

        Date fromDate = DateUtil.clearTimePart(AttendDateUtil.getCurrentDate());
        Date toDate = AttendDateUtil.addDayOfMonth(fromDate, 7); // 一週間後
        return practiceSvc.searchListForMail(fromDate, toDate);
    }

    /**
     * メールを送信します。
     * 
     * @param practiceList
     */
    private void sendMail(List<Practice> practiceList) {
        String subject = makeSubject();
        String body = makeBody(practiceList);
        mailSendSvc.sendMailToML(subject, body);
    }

    /**
     * メールタイトルを作成します。
     * 
     * @return
     */
    private String makeSubject() {
        return "今週の練習のお知らせです。";
    }

    /**
     * メール本文を作成します。
     * 
     * @param practice
     * @return
     */
    private String makeBody(List<Practice> practiceList) {

        String headerStr = "今週の練習のお知らせです。今週は以下の通り練習があります。\n自分の出欠を確認し、違う場合はMEKAZAWAで修正して下さい。\n\n";
        String contentStr = makeContent(practiceList);
        String footerStr = "練習日誌や練習準備については以下のサイトをご覧ください。\nhttps://sites.google.com/site/abeinuhigashi/renshunisshi\n\n" + "このメールはMEKAZAWAより自動配信されています。何かおかしな点があればマネージャに連絡下さい。";

        return headerStr + contentStr + footerStr;
    }

    /**
     * 練習情報から文面を作成します。
     * 
     * @param practiceList
     * @return
     */
    private String makeContent(List<Practice> practiceList) {

        StringBuffer bufStr = new StringBuffer();

        for (Practice practice : practiceList) {
            bufStr.append(makePracticeStr(practice)
                + "\n"
                + makeAttendanceStr(practice)
                + "\n\n");
        }

        return bufStr.toString();
    }

    /**
     * メール文面のうち練習情報に関わる文面を作成します。
     * 
     * @param practice
     * @return
     */
    private String makePracticeStr(Practice practice) {
        String startDateStr =
            DateUtil.toString(practice.getStartDate(), "MM月dd日 HH:mm～");
        String practicePlace = practice.getPracticePlace();

        if (StringUtil.isEmpty(practicePlace)) {
            practicePlace = "未定";
        }
        return "● " + startDateStr + "＠" + practicePlace;
    }

    /**
     * メール文面のうち出席者に関わる文面を作成します。
     * 
     * @param practice
     * @return
     */
    private String makeAttendanceStr(Practice practice) {
        List<Attendance> attendanceList =
            attendanceSvc.searchFromPracticeDate(practice.getStartDate());

        StringBuffer sopAttendStr = new StringBuffer();
        StringBuffer altoAttendStr = new StringBuffer();
        StringBuffer tenAttendStr = new StringBuffer();
        StringBuffer bassAttendStr = new StringBuffer();

        for (Attendance attendance : attendanceList) {
            if (attendance.getAttendance() == Constants.ATTENDANCE_PRESENCE
                || attendance.getAttendance() == Constants.ATTENDANCE_TARDINESS
                || attendance.getAttendance() == Constants.ATTENDANCE_EARLYLEAVE) {
                Member member = attendance.getMemberRef().getModel();
                String nameStr = member.getNickName();
                if(StringUtil.isEmpty(nameStr)){
                    nameStr = member.getLastName();
                }

                if (attendance.getAttendance() == Constants.ATTENDANCE_TARDINESS) {
                    nameStr = nameStr + "（遅刻）";
                } else if (attendance.getAttendance() == Constants.ATTENDANCE_EARLYLEAVE) {
                    nameStr = nameStr + "（早退）";
                }

                // パートでソートして表示するためにこのようにする。
                if (Constants.PART_SOPRANO.equals(member.getPart())) {
                    sopAttendStr.append(nameStr + "、");
                } else if (Constants.PART_ALTO.equals(member.getPart())) {
                    altoAttendStr.append(nameStr + "、");
                } else if (Constants.PART_TENOR.equals(member.getPart())) {
                    tenAttendStr.append(nameStr + "、");
                } else if (Constants.PART_BASS.equals(member.getPart())) {
                    bassAttendStr.append(nameStr + "、");
                }
            }
        }

        return "出席予定者は、"
            + sopAttendStr.toString()
            + altoAttendStr.toString()
            + tenAttendStr.toString()
            + bassAttendStr.toString()
            + "です。";
    }

}
