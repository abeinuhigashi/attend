package slim3.helper;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;

/**
 * 表の行情報を保持するクラス
 * 行ヘッダとセルのリストから構成される
 */
public class AttendanceTableRow{

    private AttendanceTableRowHeader rowHeader;
    
    private List<AttendanceTableCell> cellList;
    
    public AttendanceTableRow(Key memberKey, List<Key> practiceKeyList) {
        
        // 行ヘッダの作成
        rowHeader = new AttendanceTableRowHeader(memberKey);
        
        // 行セルリストの作成
        cellList = new ArrayList<AttendanceTableCell>();        
        for(Key practiceKey : practiceKeyList) {                
            AttendanceTableCell cell =
                new AttendanceTableCell(memberKey, practiceKey);
            cellList.add(cell);
        }
    }

    public AttendanceTableRowHeader getRowHeader() {
        return rowHeader;
    }

    public void setRowHeader(AttendanceTableRowHeader rowHeader) {
        this.rowHeader = rowHeader;
    }

    public List<AttendanceTableCell> getCellList() {
        return cellList;
    }

    public void setCellList(List<AttendanceTableCell> cellList) {
        this.cellList = cellList;
    }
    
}