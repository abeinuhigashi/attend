package slim3.controller.attend.inputAttendance.blank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;
import slim3.service.AttendanceService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

public class IndexController extends Controller {

    private static PracticeService practiceSvc = new PracticeService();
    private static AttendanceService attendanceSvc = new AttendanceService();

    @Override
    public Navigation run() throws Exception {

        // ログインをしていない場合は、ログインページに遷移する。
        if(sessionScope(Constants.SESSION_KEY_LOGIN_USER) == null) {
            requestScope(Constants.ATTRKEY_FROM_PATH, request.getRequestURL());
            return forward(Constants.PATH_LOGIN_ERROR);
        }

        Date currentDate = AttendDateUtil.getCurrentDate();
        List<Practice> practiceList = practiceSvc.searchList(currentDate, AttendDateUtil.addMonth(currentDate, 12));
        Member loginUser = sessionScope(Constants.SESSION_KEY_LOGIN_USER);

        // 表示対象の出欠情報を取得する
        List<Attendance> attendanceList = new ArrayList<Attendance>();
        for(Practice practice : practiceList) {
            Attendance attendance = attendanceSvc.searchFromMemberKeyAndPracticeKey(loginUser.getKey(), practice.getKey());
            if(attendance == null) {
                attendance = attendanceSvc.getInitalizedAttendance(loginUser,practice);
                attendanceSvc.regist(attendance);                
            } else if (attendance.getAttendance() != Constants.ATTENDANCE_NOT_INPUT){ // 未入力じゃなければ表示しない
                continue;
            }
            
            attendanceList.add(attendance);            
        }
        
        // 表示対象の出欠情報が無ければ、nullとする。（画面表示のため）
        if(attendanceList.size() == 0) {
            attendanceList = null;
        }
        
        // SubmitControllerからの遷移時にメッセージが格納されるため、ここで拾う
        String message = asString("message");
        errors.put("message", message);

        // 画面表示項目をrequestScopeに格納
        request.setAttribute("attendanceList", attendanceList);

        return forward("index.jsp");
    }
}
