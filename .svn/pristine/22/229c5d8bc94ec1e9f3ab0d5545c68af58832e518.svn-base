package slim3.controller.attend.async.attendance;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.service.AttendanceService;

import com.google.appengine.api.datastore.Key;

public class UpdateController extends Controller {
    
    private AttendanceService attendanceSvc = new AttendanceService();

    @Override
    public Navigation run() throws Exception {
        
        Integer inputAttendance = asInteger("inputAttendance");
        String inputRacital = asString("inputRacital");
        Key memberKey = asKey("memberKey");                
        Key practiceKey = asKey("practiceKey");
        
        if(inputAttendance == null || memberKey == null || practiceKey == null) {
            return null;            
        }
        if(inputRacital == null){
            attendanceSvc.updateIfNotExistInsert(inputAttendance, memberKey, practiceKey);
        } else {
            attendanceSvc.updateIfNotExistInsert(inputAttendance, inputRacital, memberKey, practiceKey);            
        }
        
        return null;
    }
}
