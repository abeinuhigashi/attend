package slim3.model;

import java.io.Serializable;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private int attendance;
    
    private String racital;
    
    private boolean finished;
    
    private boolean inputFlg;
    
    private Key memberKey;
    
    private Key practiceKey;

    private ModelRef<Member> memberRef = new ModelRef<Member>(Member.class);
    
    private ModelRef<Practice> practiceRef = new ModelRef<Practice>(Practice.class);
    
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
        Attendance other = (Attendance) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setRacital(String racital) {
        this.racital = racital;
    }

    public String getRacital() {
        return racital;
    }

    public Key getMemberKey() {
        return memberKey;
    }

    public void setMemberKey(Key memberKey) {
        this.memberKey = memberKey;
    }

    public Key getPracticeKey() {
        return practiceKey;
    }

    public void setPracticeKey(Key practiceKey) {
        this.practiceKey = practiceKey;
    }

    public ModelRef<Member> getMemberRef() {
        return memberRef;
    }

    public ModelRef<Practice> getPracticeRef() {
        return practiceRef;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean getFinished() {
        return finished;
    }

    public boolean getInputFlg() {
        return inputFlg;
    }

    public void setInputFlg(boolean inputFlg) {
        this.inputFlg = inputFlg;
    }
}
