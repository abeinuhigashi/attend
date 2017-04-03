package slim3.helper;

import slim3.model.Member;
import slim3.service.MemberService;

import com.google.appengine.api.datastore.Key;

/**
 * 表の行ヘッダの情報を保持するクラス
 */
public class AttendanceTableRowHeader {
    
    private String memberId;
    
    private String memberName;
    
    private Key memberKey;
    
    AttendanceTableRowHeader(Key memberKey) {
        
        MemberService memberSvc = new MemberService();
        Member member = memberSvc.searchFromKey(memberKey);
        
        this.memberId = member.getId();
        this.memberName = member.getLastName() + " " + member.getFirstName();
        this.memberKey = member.getKey();
    }    
    
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Key getMemberKey() {
        return memberKey;
    }

    public void setMemberKey(Key memberKey) {
        this.memberKey = memberKey;
    }
        
}