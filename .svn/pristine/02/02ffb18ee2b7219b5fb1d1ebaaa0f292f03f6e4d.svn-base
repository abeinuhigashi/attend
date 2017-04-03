package slim3.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Practice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // 開始日時
    private Date startDate;

    // 終了日時
    private Date endDate;
    
    // 練習場所
    private String practicePlace;
    
    // 集合場所
    private String gatheringPoint;
    
    // 集合日時
    private Date gatheringDate;
    
    // 練習曲
    private List<String> music;
    
    // 備考
    private String recital;
    
    // メール送信対象かどうか
    private boolean needSendMail;
    
    // 出欠計算対象かどうか
    private boolean needCalc;
    
    @Attribute(persistent = false)
    private InverseModelListRef<Attendance, Practice> attendanceRef =
        new InverseModelListRef<Attendance, Practice>(
            Attendance.class,
            "practiceRef",
            this);
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPracticePlace() {
        return practicePlace;
    }

    public void setPracticePlace(String practicePlace) {
        this.practicePlace = practicePlace;
    }

    public String getGatheringPoint() {
        return gatheringPoint;
    }

    public void setGatheringPoint(String gatheringPoint) {
        this.gatheringPoint = gatheringPoint;
    }

    public Date getGatheringDate() {
        return gatheringDate;
    }

    public void setGatheringDate(Date gatheringDate) {
        this.gatheringDate = gatheringDate;
    }

    public List<String> getMusic() {
        return music;
    }

    public void setMusic(List<String> music) {
        this.music = music;
    }

    public String getRecital() {
        return recital;
    }

    public void setRecital(String recital) {
        this.recital = recital;
    }

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Practice other = (Practice) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public InverseModelListRef<Attendance, Practice> getAttendanceRef() {
        return attendanceRef;
    }

    public void setNeedSendMail(boolean needSendMail) {
        this.needSendMail = needSendMail;
    }

    public boolean isNeedSendMail() {
        return needSendMail;
    }

    public void setNeedCalc(boolean needCalc) {
        this.needCalc = needCalc;
    }

    public boolean isNeedCalc() {
        return needCalc;
    }
}
