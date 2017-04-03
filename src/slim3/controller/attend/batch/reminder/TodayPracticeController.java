package slim3.controller.attend.batch.reminder;

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

public class TodayPracticeController extends Controller {

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
        List<Practice> practiceList = getTodayPracticeList();

        // 練習がある場合、メールを作成し送信する。
        if (practiceList != null && practiceList.size() > 0) {
            sendMail(practiceList.get(0));
            logger
                .info("メールを送信しました。対象練習：" + practiceList.get(0).getStartDate());
        } else {
            logger.info("該当する練習がないためメールを送信しませんでした。");
        }

        return null;
    }

    /**
     * 本日の練習一覧を取得する。
     * 
     * @return
     */
    private List<Practice> getTodayPracticeList() {
        return practiceSvc.searchForMail(AttendDateUtil.getCurrentDate());
    }

    /**
     * メールを送信します。
     * 
     * @param practice
     */
    private void sendMail(Practice practice) {

        // タイトルの作成
        String subject = makeSubject();

        // 本文の作成
        String body = makeBody(practice);

        // メールの送信
        mailSendSvc.sendMailToML(subject, body);

    }

    /**
     * メールタイトルを作成します。
     * 
     * @return
     */
    private String makeSubject() {
        return "本日練習があります。";
    }

    /**
     * メール本文を作成します。
     * 
     * @param practice
     * @return
     */
    private String makeBody(Practice practice) {

        // 練習情報に関する文面を作成
        String practiceStr = makePracticeStr(practice);

        // 集合情報に関する文面を作成
        String gatheringStr = makeGatheringStr(practice);

        // 出席情報に関する文面を作成
        String attendanceStr = makeAttendanceStr(practice);

        return "本日は練習日です。\n"
            + practiceStr
            + "\n"
            + gatheringStr
            + "\n"
            + attendanceStr
            + "\n\n"
            + "練習日誌や練習準備については以下のサイトをご覧ください。\nhttps://sites.google.com/site/abeinuhigashi/renshunisshi\n\nこのメールはMEKAZAWAより自動送信されています。何かおかしな点があればマネージャに連絡下さい。";
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

        return "出席者は、"
            + sopAttendStr.toString()
            + altoAttendStr.toString()
            + tenAttendStr.toString()
            + bassAttendStr.toString()
            + "です。";
    }

    /**
     * メール文面のうち集合情報に関わる文面を作成します。
     * 
     * @param practice
     * @return
     */
    private String makeGatheringStr(Practice practice) {

        String gatheringPointStr = practice.getGatheringPoint();
        String gatheringDateStr =
            DateUtil.toString(practice.getGatheringDate(), "HH:mm");

        String rtnStr = null;
        if (StringUtil.isEmpty(gatheringPointStr)
            || StringUtil.isEmpty(gatheringDateStr)) {
            rtnStr = "集合情報は設定されていません。";
        } else {
            rtnStr = gatheringPointStr + "に" + gatheringDateStr + "に集合して下さい。";
        }
        return rtnStr;
    }

    /**
     * メール文面のうち練習情報に関わる文面を作成します。
     * 
     * @param practice
     * @return
     */
    private String makePracticeStr(Practice practice) {
        String startDateStr =
            DateUtil.toString(practice.getStartDate(), "HH:mm");
        String practicePlace = practice.getPracticePlace();

        if (StringUtil.isEmpty(practicePlace)) {
            practicePlace = "未定";
        }
        return "開始時間は" + startDateStr + "。場所は" + practicePlace + "です。";
    }
}
