package slim3.controller.attend.manage.attendance;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.helper.AttendanceDispTable;
import slim3.service.MemberService;
import slim3.service.PracticeService;

import com.google.appengine.api.datastore.Key;

public class DispController extends Controller {

    private static MemberService memberSvc = new MemberService();
    private static PracticeService practiceSvc = new PracticeService();

    @Override
    public Navigation run() throws Exception {
        
        Key practiceKey = asKey("key");

        // 表示範囲のfromDateを算出する。
        Date startDate = null;
        if(practiceKey == null) {
            startDate = getCurrentDate();
        } else {
            startDate = practiceSvc.searchFromKey(practiceKey).getStartDate();
        }
        
        // 表示数が指定されていた場合、取得する
        Integer limit = asInteger("limit");
        if(limit == null) {
            limit = new Integer(5);
        }
        
        // 表示モードを取得する。
        String modeStr = asString("mode");
        
        // 表示練習日一覧を取得する。
        List<Key> practiceKeyList = null;
        
        // 基準日より過去日を取得する
        if(modeStr != null && modeStr.equals("past")){
            practiceKeyList = practiceSvc.searchKeyListToDate(startDate, limit);
            // 過去日が取得できない場合は、エラーメッセージの設定と基準日から未来日を取得する。
            if(practiceKeyList == null || practiceKeyList.isEmpty()){
                errors.put("selectError", "過去日が存在しません。");
                practiceKeyList = practiceSvc.searchKeyListFromDate(startDate, limit);                
            }
        // 基準日より未来日を取得する
        } else {
            practiceKeyList = practiceSvc.searchKeyListFromDate(startDate, limit);
            if(practiceKeyList == null || practiceKeyList.isEmpty()){
                practiceKeyList = practiceSvc.searchKeyListToDate(startDate, limit);                
            }            
        }

        // 表示メンバーを取得する
        List<Key> memberKeyList = memberSvc.getAllMemberKeySortedByPart();

        if(practiceKeyList == null|| practiceKeyList.isEmpty()){
            errors.put("selectError", "練習日が存在しません。");
        }
        // 一覧表を作成する
        AttendanceDispTable table = new AttendanceDispTable(memberKeyList, practiceKeyList);        
        requestScope("dispTable", table);
        
        return forward("disp.jsp");
    }
    
    /**
     * requestParameters から現在の年月を取得します
     * requestParameters に存在しない場合はシステム日付から取得します。（JST）
     * 
     * @return
     */
    private Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 9);
        return cal.getTime();
    }    
}
