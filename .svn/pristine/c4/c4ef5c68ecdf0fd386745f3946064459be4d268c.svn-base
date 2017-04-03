package slim3.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import slim3.service.PracticeService;

import com.google.appengine.api.datastore.Key;

public 
/**
 * 表の列ヘッダリストの情報を保持するクラス
 */
class AttendanceTableColumnHeader{
    
    private List<Date> dateList;
    private List<Key> keyList;
    
    AttendanceTableColumnHeader() {
        dateList = new ArrayList<Date>();
    }
    
    AttendanceTableColumnHeader(List<Key> practiceList) {
        keyList = practiceList;
        dateList = new ArrayList<Date>();

        PracticeService practiceSvc = new PracticeService();
        for(Key practiceDateKey : practiceList) {
            dateList.add(practiceSvc.searchFromKey(practiceDateKey).getStartDate());
        }        
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }

    public List<Key> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<Key> keyList) {
        this.keyList = keyList;
    }
}