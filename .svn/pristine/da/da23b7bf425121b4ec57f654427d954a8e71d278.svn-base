package slim3.helper;

import java.util.Date;


import slim3.model.Attendance;
import slim3.service.AttendanceService;

import com.google.appengine.api.datastore.Key;
/**
 * 表のセル情報を保持するクラス
 */
public class AttendanceTableCell {
    
    private Date practiceDate;
    
    private Key practiceKey;

    private int attendance;
    
    public AttendanceTableCell(Key memberKey, Key practiceKey) {
        this.practiceKey = practiceKey;
        AttendanceService attendanceSvc = new AttendanceService();
        Attendance attendance = attendanceSvc
        .searchFromMemberKeyAndPracticeKey(
            memberKey,
            practiceKey);
        
        if(attendance != null) {
            this.attendance = attendance.getAttendance();
        } else {
            this.attendance = 0; // TODO 定数化
        }
    }

    public Date getPracticeDate() {
        return practiceDate;
    }

    public void setPracticeDate(Date practiceDate) {
        this.practiceDate = practiceDate;
    }

    public Key getPracticeKey() {
        return practiceKey;
    }

    public void setPracticeKey(Key practiceDateKey) {
        this.practiceKey = practiceDateKey;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    
}
