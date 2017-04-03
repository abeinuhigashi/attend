package slim3.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;
import org.slim3.util.BeanUtil;
import org.slim3.util.StringUtil;

import slim3.constants.Constants;
import slim3.meta.MemberMeta;
import slim3.model.Member;
import slim3.util.AttendDateUtil;

import com.google.appengine.api.datastore.Key;

public class MemberService {

    private MemberMeta t = new MemberMeta();

    private MailSendService mailSendSvc = new MailSendService();

    /**
     * IDとPasswordでユーザー情報を取得します
     * 
     * @param id
     * @param password
     * @return Member
     */
    public Member login(String id, String password) {

        if (id == null || password == null) {
            return null;
        }

        Member member = searchFromIdAndPassword(id, password);
        if (member == null) {
            return null;
        }
        
        return updateLastLoginInfo(member);
    }

    /**
     * Keyでログインします。
     * 
     * @param cookieKey
     * @return
     */
    public Member login(String cookieKey) {

        if (StringUtil.isEmpty(cookieKey)) {
            return null;
        }

        Key key = Datastore.stringToKey(cookieKey);
        Member member = searchFromKey(key);
        if (member == null) {
            return null;
        }

        return updateLastLoginInfo(member);
    }
    
    /**
     * 対象Memberのログインキー（クッキー用）を取得する
     * 
     * @param member
     * @return
     */
    public String getLoginCookieKey(Member member) {

        if (member == null) {
            return null;
        }

        return Datastore.keyToString(member.getKey());
    }

    /**
     * Memberを一人登録する。 ＩＤがない場合、また同じＩＤが存在する場合はエラー
     * 
     * @param input
     * @return
     * @throws IllegalArgumentException
     */
    public Member regist(Map<String, Object> input)
            throws IllegalArgumentException {

        if (input == null) {
            throw new IllegalArgumentException("Error! Input input is null.");
        }

        Member member = new Member();
        BeanUtil.copy(input, member);

        return regist(member);

    }

    /**
     * Memberを一人登録します。
     * 
     * @param member
     * @return
     */
    public Member regist(Member member) {

        if (member == null) {
            throw new IllegalArgumentException("Error! Input member is null.");
        }

        // 社員番号がなければエラー
        if (member.getId() == null) {
            throw new IllegalArgumentException("Error! Input id is null.");
        }
        // 社員が存在すれば登録失敗
        if (searchFromId(member.getId()) != null) {
            throw new IllegalArgumentException(
                "Error! Input id already exists.");
        }

        // 初期登録時にデフォルトで入る項目
        member.setSuspended(false);
        member.setNotUsedLongTime(false);
        member.setMailUse(false);
        member.setMailError(false);

        // データ登録
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(member);
        gtx.commit();
        return member;
    }

    /**
     * Key情報でMemberを検索します。
     * 
     * @param key
     * @return Member
     */
    public Member searchFromKey(Key key) {
        return Datastore.query(t).filter(t.key.equal(key)).asSingle();
    }

    /**
     * IDでMemberを検索します
     * 
     * @param id
     * @return Member
     */
    public Member searchFromId(String id) {
        return Datastore.query(t).filter(t.id.equal(id)).asSingle();
    }

    /**
     * MemberをIDとPasswordで検索します
     * 
     * @param id
     * @return Member
     */
    public Member searchFromIdAndPassword(String id, String password)
            throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Error! Input id is null.");
        } else if (password == null) {
            throw new IllegalArgumentException("Error! Input password is null.");
        } else {
            return Datastore
                .query(t)
                .filter(t.id.equal(id), t.password.equal(password))
                .asSingle();
        }
    }

    /**
     * MemberをIDで先方一致検索します
     * 
     * @param id
     * @return Member
     */
    public List<Member> searchStartWithId(String id)
            throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Error! Input id is null.");
        } else {
            return Datastore.query(t).filter(t.id.startsWith(id)).asList();
        }
    }

    /**
     * 基準日より以前にログインしたMember一覧を取得します ※長期未入力者を洗い出すのに使用する想定
     * ログイン日時がnullのMemberは抽出対象外
     * 
     * @param baseDate
     * @return memberList
     * @throws IllegalArgumentException
     */
    public List<Member> searchFromLastLoginDateTimeBeforeBaseDate(Date baseDate)
            throws IllegalArgumentException {
        if (baseDate == null) {
            throw new IllegalArgumentException("Error! Input baseDate is null.");
        } else {
            return Datastore
                .query(t)
                .filter(
                    t.lastLoginDateTime.isNotNull(),
                    t.lastLoginDateTime.lessThanOrEqual(baseDate))
                .asList();
        }
    }

    /**
     * 全Memberを取得取得します（IDでソート）
     * 
     * @return 全Member
     */
    public List<Member> getAll() {
        return Datastore.query(t).sort(t.id.asc).asList();
    }

    /**
     * 休団していない、かつ、長期未入力でないメンバーのKeyを取得します。 SATBの順にソート。
     * 
     * @return
     */
    public List<Key> getAllMemberKeySortedByPart() {
        List<Key> rtnList = null;
        rtnList =
            Datastore
                .query(t)
                .filter(
                    t.part.equal(Constants.PART_SOPRANO),
                    t.suspended.equal(false),
                    t.notUsedLongTime.equal(false))
                .sort(t.id.asc)
                .asKeyList();
        rtnList.addAll(Datastore
            .query(t)
            .filter(
                t.part.equal(Constants.PART_ALTO),
                t.suspended.equal(false),
                t.notUsedLongTime.equal(false))
            .sort(t.id.asc)
            .asKeyList());
        rtnList.addAll(Datastore
            .query(t)
            .filter(
                t.part.equal(Constants.PART_TENOR),
                t.suspended.equal(false),
                t.notUsedLongTime.equal(false))
            .sort(t.id.asc)
            .asKeyList());
        rtnList.addAll(Datastore
            .query(t)
            .filter(
                t.part.equal(Constants.PART_BASS),
                t.suspended.equal(false),
                t.notUsedLongTime.equal(false))
            .sort(t.id.asc)
            .asKeyList());

        return rtnList;
    }

    /**
     * Member情報を更新します
     * 
     * @param member
     *            更新対象社員情報
     * @return 更新後社員情報
     * @throws IllegalArgumentException
     *             更新対象社員が存在しない場合に発生
     */
    public Member update(Member member) throws IllegalArgumentException {

        if (member == null) {
            throw new IllegalArgumentException("Error! Input member is null.");
        } else if (searchFromKey(member.getKey()) == null) {
            throw new IllegalArgumentException(
                "Error! Target member does not exist. : Input key is "
                    + member.getKey());
        } else {
            GlobalTransaction gtx = Datastore.beginGlobalTransaction();
            gtx.put(member);
            gtx.commit();
        }
        return member;
    }

    /**
     * Memberを一人更新します。
     * 
     * @param input
     * @return
     * @throws IllegalArgumentException
     */
    public Member update(Map<String, Object> input)
            throws IllegalArgumentException {

        if (input == null) {
            throw new IllegalArgumentException("Error! Input input is null.");
        }

        Member member = new Member();
        BeanUtil.copy(input, member);

        // 社員番号がなければエラー
        if (member.getId() == null) {
            throw new IllegalArgumentException("Error! Input id is null.");
        }
        // 社員が存在しなければ更新失敗
        if (searchFromKey(member.getKey()) == null) {
            throw new IllegalArgumentException(
                "Error! Input id already exists.");
        }

        // データ登録
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(member);
        gtx.commit();
        return member;
    }

    /**
     * Memberを削除します
     * 
     * @param id
     *            削除対象ID
     * @return 成功時trueを返す
     */
    public boolean delete(String id) throws IllegalArgumentException {

        if (id == null) {
            return false;
        }
        Member member = searchFromId(id);
        if (member == null) {
            return false;
        } else {
            GlobalTransaction gtx = Datastore.beginGlobalTransaction();
            gtx.delete(member.getKey());
            gtx.commit();
        }
        return true;
    }

    /**
     * 最終ログイン情報の更新を行います。
     * 
     * @param member
     * @return
     */
    private Member updateLastLoginInfo(Member member) {
        
        if(member == null){
            return null;
        }
        
        // 最終ログイン日時を更新
        member.setLastLoginDateTime(AttendDateUtil.getCurrentDate());

        // 長期未入力者の場合、解除する。
        if (member.isNotUsedLongTime()) {
            member.setNotUsedLongTime(false);
            String subject = "長期未入力者解除のお知らせ";
            String msg =
                member.getLastName()
                    + " "
                    + member.getFirstName()
                    + "さんがログインしたため、長期未入力者から除外しました。";
            mailSendSvc.sendMailToSystemAdmin(subject, msg);
        }

        return update(member);
    }
}
