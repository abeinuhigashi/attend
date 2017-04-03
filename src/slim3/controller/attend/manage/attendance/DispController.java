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

        // �\���͈͂�fromDate���Z�o����B
        Date startDate = null;
        if(practiceKey == null) {
            startDate = getCurrentDate();
        } else {
            startDate = practiceSvc.searchFromKey(practiceKey).getStartDate();
        }
        
        // �\�������w�肳��Ă����ꍇ�A�擾����
        Integer limit = asInteger("limit");
        if(limit == null) {
            limit = new Integer(5);
        }
        
        // �\�����[�h���擾����B
        String modeStr = asString("mode");
        
        // �\�����K���ꗗ���擾����B
        List<Key> practiceKeyList = null;
        
        // ������ߋ������擾����
        if(modeStr != null && modeStr.equals("past")){
            practiceKeyList = practiceSvc.searchKeyListToDate(startDate, limit);
            // �ߋ������擾�ł��Ȃ��ꍇ�́A�G���[���b�Z�[�W�̐ݒ�Ɗ�����疢�������擾����B
            if(practiceKeyList == null || practiceKeyList.isEmpty()){
                errors.put("selectError", "�ߋ��������݂��܂���B");
                practiceKeyList = practiceSvc.searchKeyListFromDate(startDate, limit);                
            }
        // �����薢�������擾����
        } else {
            practiceKeyList = practiceSvc.searchKeyListFromDate(startDate, limit);
            if(practiceKeyList == null || practiceKeyList.isEmpty()){
                practiceKeyList = practiceSvc.searchKeyListToDate(startDate, limit);                
            }            
        }

        // �\�������o�[���擾����
        List<Key> memberKeyList = memberSvc.getAllMemberKeySortedByPart();

        if(practiceKeyList == null|| practiceKeyList.isEmpty()){
            errors.put("selectError", "���K�������݂��܂���B");
        }
        // �ꗗ�\���쐬����
        AttendanceDispTable table = new AttendanceDispTable(memberKeyList, practiceKeyList);        
        requestScope("dispTable", table);
        
        return forward("disp.jsp");
    }
    
    /**
     * requestParameters ���猻�݂̔N�����擾���܂�
     * requestParameters �ɑ��݂��Ȃ��ꍇ�̓V�X�e�����t����擾���܂��B�iJST�j
     * 
     * @return
     */
    private Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 9);
        return cal.getTime();
    }    
}
