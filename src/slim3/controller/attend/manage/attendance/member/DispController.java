package slim3.controller.attend.manage.attendance.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;
import org.slim3.util.StringUtil;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.service.AttendanceService;
import slim3.service.MemberService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;
import slim3.util.SettingPropertyUtil;

import com.google.appengine.api.datastore.Key;

public class DispController extends Controller {

    private static AttendanceService attendanceSvc = new AttendanceService();
    private static PracticeService practiceSvc = new PracticeService();
    private static MemberService memberSvc = new MemberService();

    @Override
    public Navigation run() throws Exception {

        if (CheckInput()) {
            ConstractCalcData();
            requestScope("id", asString("id"));
            requestScope("term", asString("term"));
        }
        ConstractMemberList();
        ConstractDateList();
        return forward("disp.jsp");
    }

    /**
     * 入力チェック idが指定されているかどうか。
     * 
     * @return
     */
    private boolean CheckInput() {
        String id = asString("id");
        String term = asString("term");
        if (StringUtil.isEmpty(id) || StringUtil.isEmpty(term)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 画面表示用のメンバーリストを構築します
     * 
     */
    private void ConstractMemberList() {
        List<Member> memberList = memberSvc.getAll();
        requestScope("memberList", memberList);
    }

    /**
     * 画面表示用の日付リストを構築します
     * 
     */
    private void ConstractDateList() {
        List<Date> dateList = getDateList();
        requestScope("dateList", dateList);
    }

    /**
     * 画面表示用の出席率を構築します
     */
    private void ConstractCalcData() {

        // 計算対象期間の算出
        List<Date> dateList = getDateList();
        Date fromDate = dateList.get(asInteger("term"));
        Date toDate = dateList.get(asInteger("term") + 1);

        // 出席一覧を取得する
        List<Key> practiceKeyList =
            practiceSvc.searchKeyListFromDateSpanForCalc(fromDate, toDate);
        List<Attendance> attendanceList =
            attendanceSvc.searchFromMemberKeyAndPracticeKey(
                getMemberKey(),
                practiceKeyList);

        // 出席率の計算
        int numFullAttend = 0;
        int numPartAttend = 0;
        double ratio = 0.0f;
        if (attendanceList != null) {
            for (Attendance attendance : attendanceList) {
                if (attendance.getAttendance() == Constants.ATTENDANCE_PRESENCE) {
                    numFullAttend++;
                } else if (attendance.getAttendance() == Constants.ATTENDANCE_EARLYLEAVE
                    || attendance.getAttendance() == Constants.ATTENDANCE_TARDINESS) {
                    numPartAttend++;
                }
            }
            ratio =
                (numFullAttend + numPartAttend / 2.0)
                    / (double) practiceKeyList.size();
        }

        // 結果をrequestScopeへ格納
        requestScope("fromDate", fromDate);
        requestScope("toDate", AttendDateUtil.addDay(toDate, -1)); // 表示上は一日前になる
        requestScope("numPractice", practiceKeyList.size());
        requestScope("numFullAttend", numFullAttend);
        requestScope("numPartAttend", numPartAttend);
        requestScope("ratio", ratio);
        requestScope("attendanceList", attendanceList);
    }

    private List<Date> getDateList() {

        List<Date> rtnList = new ArrayList<Date>();

        String[] dateList =
            StringUtil.split(
                SettingPropertyUtil.getProperty("calc.attend.date"),
                ",");

        for (int i = 0; i < dateList.length; i++) {
            rtnList
                .add(DateUtil.toDate(dateList[i], DateUtil.ISO_DATE_PATTERN));
        }

        return rtnList;
    }

    private Key getMemberKey() {
        String id = asString("id");
        Member member = memberSvc.searchFromId(id);
        if (member != null) {
            return member.getKey();
        } else {
            return null;
        }
    }
}
