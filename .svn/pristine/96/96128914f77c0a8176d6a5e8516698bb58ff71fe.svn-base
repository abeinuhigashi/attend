package slim3.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String id;

    private String password;

    private String firstName;

    private String lastName;
    
    private String nickName;

    private Date birthDay;

    private String part;

    private String mailAddress;

    private int authority;
    
    private boolean suspended;
    
    private boolean notUsedLongTime;
    
    private Date lastLoginDateTime;

    private boolean mailUse;
    
    private boolean mailError;
    
    @Attribute(persistent = false)
    private InverseModelListRef<Attendance, Member> attendanceRef =
        new InverseModelListRef<Attendance, Member>(
            Attendance.class,
            "memberRef",
            this);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPart() {
        return part;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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
        Member other = (Member) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InverseModelListRef<Attendance, Member> getAttendanceRef() {
        return attendanceRef;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getAuthority() {
        return authority;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean getSuspended() {
        return suspended;
    }

    public boolean isNotUsedLongTime() {
        return notUsedLongTime;
    }

    public void setNotUsedLongTime(boolean notUsedLongTime) {
        this.notUsedLongTime = notUsedLongTime;
    }

    public Date getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    public void setLastLoginDateTime(Date lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public boolean isMailUse() {
        return mailUse;
    }

    public void setMailUse(boolean mailUse) {
        this.mailUse = mailUse;
    }

    public boolean isMailError() {
        return mailError;
    }

    public void setMailError(boolean mailError) {
        this.mailError = mailError;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
    
}
