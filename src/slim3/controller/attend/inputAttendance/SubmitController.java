package slim3.controller.attend.inputAttendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.model.Attendance;
import slim3.service.AttendanceService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class SubmitController extends Controller {

    private AttendanceService attendanceSvc = new AttendanceService();
    private PracticeService practiceSvc = new PracticeService();

    @Override
    public Navigation run() throws Exception {

        if (asString("currentDate") == null) {
            // フォームの情報が存在しない場合はTopPageに遷移する事でエラー回避（F5を想定）
            return forward("/attend/");
        }

        String[] keyArray = requestScope("keyArray");
        Integer index = asInteger("index");
        boolean isAll = asBoolean("allFlg");

        if (!isAll) {
            if (isFuture(index)) {// 未来日のみ更新(１行のみの更新でも一応チェック)
                attendanceSvc.regist(ContstructAttendance(index));
            } else {
                errors.put("cannotUpdate", "その日はもう過去日だから更新できないよ！");
            }
        } else {

            int recordCount = keyArray.length;// 便宜上keyArrayからレコードのサイズを取得
            List<Attendance> attendanceList = new ArrayList<Attendance>();
            for (int i = 0; i < recordCount; i++) {
                if (isFuture(i)) {
                    // 未来日のみ更新リストに追加
                    attendanceList.add(ContstructAttendance(i));
                }
            }
            try {
                attendanceSvc.registList(attendanceList);
            } catch (IllegalArgumentException e) {
                errors.put("cannotUpdate", "よくわかんないけど登録に失敗しました。再度登録を行ってみてください。");
            }
        }

        return forward(basePath + "?date=" + asString("currentDate"));
    }

    /**
     * Request情報から練習情報を生成します
     *
     * @return
     */
    private Attendance ContstructAttendance(int index) {

        String[] memberKeyArray = requestScope("memberKeyArray");
        String[] practiceKeyArray = requestScope("practiceKeyArray");
        String[] attendanceArray = requestScope("attendanceArray");
        String[] racitalArray = requestScope("racitalArray");

        Attendance attendance = new Attendance();
        attendance.setAttendance(Integer.parseInt(attendanceArray[index]));
        attendance.setRacital(racitalArray[index]);
        attendance.setMemberKey(KeyFactory.stringToKey(memberKeyArray[index]));
        attendance.setPracticeKey(KeyFactory
            .stringToKey(practiceKeyArray[index]));
        return attendance;
    }

    private boolean isFuture(int index) {

        String[] practiceKeyArray = requestScope("practiceKeyArray");
        Key practiceKey = KeyFactory.stringToKey(practiceKeyArray[index]);

        Date startDate = practiceSvc.searchFromKey(practiceKey).getStartDate();

        if (AttendDateUtil.checkPast(startDate)) {
            return false;
        } else {
            return true;
        }
    }

}