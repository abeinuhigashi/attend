package slim3.helper;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;

public class AttendanceDispTable {
    
    private AttendanceTableColumnHeader columnHeader;

    private List<AttendanceTableRow> rowList;

    public AttendanceDispTable(List<Key> memberKeyList, List<Key> practiceKeyList){
        
        // 列ヘッダ部の作成
        columnHeader = new AttendanceTableColumnHeader(practiceKeyList);

        // 行リストの作成
        rowList = new ArrayList<AttendanceTableRow>();
        for(Key memberKey : memberKeyList) {
            AttendanceTableRow row = new AttendanceTableRow(memberKey, practiceKeyList);
            rowList.add(row);
        }
    }
    
    public AttendanceTableColumnHeader getColumnHeader() {
        return columnHeader;
    }

    public void setColumnHeader(AttendanceTableColumnHeader columnHeader) {
        this.columnHeader = columnHeader;
    }
    
    public List<AttendanceTableRow> getRowList() {
        return rowList;
    }

    public void setRow(List<AttendanceTableRow> rowList) {
        this.rowList = rowList;
    }
}
