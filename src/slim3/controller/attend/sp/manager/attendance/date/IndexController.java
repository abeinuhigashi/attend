package slim3.controller.attend.sp.manager.attendance.date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;
import slim3.service.AttendanceService;
import slim3.service.MemberService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

import com.google.appengine.api.datastore.Key;

public class IndexController extends Controller {

    private static MemberService memberSvc = new MemberService();
    private static PracticeService practiceSvc = new PracticeService();
    private static AttendanceService attendanceSvc = new AttendanceService();

    @Override
    public Navigation run() throws Exception {
                
        // 権限がない場合は、エラーページに遷移する。
        // TODO スマホ版権限フィルタの作成
        @SuppressWarnings("unchecked")
        Map<String, Integer> auth =
            (Map<String, Integer>) sessionScope(Constants.SESSION_KEY_LOGIN_USER_AUTH);
        int attendanceAuth = auth.get(Constants.MAP_KEY_ATTENDANCE);
        if (attendanceAuth != Constants.AUTH_UPDATE) {
            errors.put("msg", "権限がありません。マネージャーにお問い合わせ下さい。");
            return forward(Constants.PATH_ERROR_SP);
        }
        
        // 入力パラメータのチェック
        Key practiceKey = asKey("key");
        
        // 練習日が指定されていなかったら、現在日付からもっとも近い次の練習日を取得して表示する
        if (practiceKey == null) {
            practiceKey = practiceSvc.search(AttendDateUtil.getCurrentDate(), 1).get(0).getKey();
        }

        // 前へ、次へがクリックされていたら、表示日を変更する
        if (asString("prev") != null) {
            practiceKey = practiceSvc.getPreviousPracticeKey(practiceKey, 1);
            if (practiceKey == null) {
                errors.put("inputError", "練習が存在しません。");
                return forward("disp.jsp");
            }
        }
        if (asString("next") != null) {
            practiceKey = practiceSvc.getNextPracticeKey(practiceKey, 1);
            if (practiceKey == null) {
                errors.put("inputError", "練習が存在しません。");
                return forward("disp.jsp");
            }
        }

        // １つ前の練習日が存在しない場合、前へボタンを非表示とする。
        if(practiceSvc.getPreviousPracticeKey(practiceKey, 1) == null) {
            requestScope("prevButtonDisp", false);
        }
        // １つ先の練習日が存在しない場合、次へボタンを非表示とする。
        if(practiceSvc.getNextPracticeKey(practiceKey, 1) == null) {
            requestScope("nextButtonDisp", false);
        }

        // 団員が存在しない場合はエラー表示
        List<Key> memberKeyList = memberSvc.getAllMemberKeySortedByPart();
        if (memberKeyList == null || memberKeyList.isEmpty()) {
            errors.put("inputError", "団員がいません。");
            return forward("index.jsp");
        }

        // 表示すべき出欠一覧情報を構築し画面に渡す。
        String[] selectedAttendanceArray =
            request.getParameterValues("selectedAttendanceArray");
        ConstructDispTable(practiceKey, memberKeyList, selectedAttendanceArray);

        // 更新可能かどうかを判断し、画面に渡す。
        checkCanUpdate(practiceKey);
        
        return forward("index.jsp");
    }
    
    /**
     * 更新が出来るかどうか判定し、その情報をrequestScopeに格納する
     * ログインユーザーの権限が不足している場合 or 未来日の場合は更新は出来ないように画面で制御する。
     * 
     * @param practiceKey
     */
    private void checkCanUpdate(Key practiceKey) {
        // ログインユーザーの出欠権限情報から、出欠の更新可否を算出
        Map<String, Integer> authMap = sessionScope(Constants.SESSION_KEY_LOGIN_USER_AUTH);
        int attendanceAuth = authMap.get(Constants.MAP_KEY_ATTENDANCE);
        boolean updateDisabled = true;
        if(attendanceAuth == Constants.AUTH_UPDATE) {
            updateDisabled = false;
        }
        
        // 未来日の場合は出欠が更新できない条件を付加
        Practice practice = practiceSvc.searchFromKey(practiceKey);
        Date currentDate = AttendDateUtil.getCurrentDate();
        if (currentDate.compareTo(DateUtil.clearTimePart(practice
            .getStartDate())) < 0) {
            updateDisabled = true;
        }

        requestScope("updateDisabled", updateDisabled);
    }

    /**
     * 表示すべき出席表一覧を構築し、requestScopeに格納する
     * 全体の出席者数、各パートの出席者数も算出し、requestScopeに格納する
     * 
     * @param practiceKey 練習日のキー
     * @param memberKeyList 表示対象の団員リスト
     * @param selectedAttendanceArray 表示対象の出欠情報リスト
     */
    private void ConstructDispTable(Key practiceKey, List<Key> memberKeyList,
            String[] selectedAttendanceArray) {

        Practice practice = practiceSvc.searchFromKey(practiceKey);
        List<Attendance> dispAttendanceList = new ArrayList<Attendance>();
        List<Member> dispMemberList = new ArrayList<Member>();

        int totalAttendance = 0;
        int sopAttendance = 0;
        int altoAttendance = 0;
        int tenAttendance = 0;
        int bassAttendance = 0;
        
        for (Key memberKey : memberKeyList) {
            Attendance attendance =
                attendanceSvc.searchFromMemberKeyAndPracticeKey(
                    memberKey,
                    practiceKey);

            Member member = memberSvc.searchFromKey(memberKey);

            // 出欠が登録されていない場合は初期化したものを取得する。
            if (attendance == null) {
                attendance =
                    attendanceSvc.getInitalizedAttendance(member, practice);
                attendanceSvc.regist(attendance);
            }

            // 表示対象のものだけListに登録する。
            if (isDisp(attendance.getAttendance(), selectedAttendanceArray)) {
                dispAttendanceList.add(attendance);
                dispMemberList.add(member);
            }
            
            // 出欠総数を算出する。
            if (attendance.getAttendance() == Constants.ATTENDANCE_PRESENCE
                || attendance.getAttendance() == Constants.ATTENDANCE_EARLYLEAVE
                || attendance.getAttendance() == Constants.ATTENDANCE_TARDINESS) {

                if (Constants.PART_SOPRANO.equals(member.getPart())) {
                    sopAttendance++;
                } else if (Constants.PART_ALTO.equals(member.getPart())) {
                    altoAttendance++;
                } else if (Constants.PART_TENOR.equals(member.getPart())) {
                    tenAttendance++;
                } else if (Constants.PART_BASS.equals(member.getPart())) {
                    bassAttendance++;
                }
                
                totalAttendance++;
            }
        }

        if (dispAttendanceList.isEmpty()) {
            dispAttendanceList = null;
        }
        if (dispMemberList.isEmpty()) {
            dispMemberList = null;
        }

        requestScope("dispAttendanceList", dispAttendanceList);
        requestScope("dispMemberList", dispMemberList);
        requestScope("dispPractice", practice);
        requestScope("totalAttendance", totalAttendance);
        requestScope("sopAttendance", sopAttendance);
        requestScope("altoAttendance", altoAttendance);
        requestScope("tenAttendance", tenAttendance);
        requestScope("bassAttendance", bassAttendance);
    }

    /**
     * 表示すべきかどうか判定する
     * selectedAttendanceArrayがnullの場合は無条件に表示する。
     * 
     * @param attendance 出席情報
     * @param selectedAttendanceArray 表示対象の出席情報
     * @return
     */
    private boolean isDisp(int attendance, String[] selectedAttendanceArray) {

        // 表示対象がnullならば全て表示する
        if (selectedAttendanceArray == null
            || selectedAttendanceArray.length == 0) {
            return true;
        }

        // 表示する出欠が、表示対象に含まれていれば表示する
        for (String selectedAttendance : selectedAttendanceArray) {
            if (attendance == Integer.valueOf(selectedAttendance)) {
                return true;
            }
        }

        return false;
    }    
}
