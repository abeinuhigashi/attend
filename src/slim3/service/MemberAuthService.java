package slim3.service;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

import com.google.appengine.api.datastore.Key;

import slim3.constants.Constants;
import slim3.meta.MemberAuthMeta;
import slim3.model.Member;
import slim3.model.MemberAuth;


public class MemberAuthService {

    MemberAuthMeta t = new MemberAuthMeta();

    public MemberAuth searchFromMemberKey(Member member){
        return Datastore.query(t).filter(t.memberRef.equal(member.getKey())).asSingle();
    }

    public MemberAuth searchFromKey(Key key){
        return Datastore.query(t).filter(t.key.equal(key)).asSingle();
    }

    public MemberAuth regist(MemberAuth memberAuth){

        // 必須情報の確認
        if(memberAuth == null) {
            throw new IllegalArgumentException("Error! Input memberAuth is null.");
        }

        if(memberAuth.getMemberRef().getModel() == null){
            //メンバーへのリレーションが張られていなければエラー
            throw new IllegalArgumentException("Error! Input memberAuth's relation to member is null.");
        }

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(memberAuth);
        gtx.commit();

        return memberAuth;
    }


    /**
     * 団員権限初期登録用メソッド<br/>
     * 引数で渡されたMemberに紐づく団員権限情報を作成し登録。
     *  <br/>
     * 初期値は下記参照
     *
     * 出席情報：なし
     * メンバ情報：なし
     * 練習日情報：なし
     * 団員権限情報：なし
     *
     * @param member
     * @return
     */
    public MemberAuth registAsInitial(Member member){

        // 必須情報の確認
        if(member == null) {
            throw new IllegalArgumentException("Error! Input member is null.");
        }

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAttendance(Constants.AUTH_NOTHING);
        memberAuth.setMember(Constants.AUTH_NOTHING);
        memberAuth.setPractice(Constants.AUTH_NOTHING);
        memberAuth.setMemberAuth(Constants.AUTH_NOTHING);
        memberAuth.getMemberRef().setModel(member);


        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(memberAuth);
        gtx.commit();

        return memberAuth;
    }

}
