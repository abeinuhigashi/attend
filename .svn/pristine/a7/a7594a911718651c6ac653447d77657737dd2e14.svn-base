package slim3.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;
import org.slim3.util.BeanUtil;

import slim3.meta.AttendanceMeta;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;

import com.google.appengine.api.datastore.Key;

public class AttendanceService {

    private AttendanceMeta t = new AttendanceMeta();
    private MemberService memberSvc = new MemberService();
    private PracticeService practiceSvc = new PracticeService();

    /**
     * 出欠情報を登録する時の前処理 出欠情報の入力チェックおよび、refModelの追加。
     * 
     * @param attendance
     *            登録対象の出欠情報
     * @return 構築後の出欠情報
     * @throws IllegalArgumentException
     *             入力エラー発生時
     */
    private Attendance preRegist(Attendance attendance)
            throws IllegalArgumentException {
        // 必須情報の確認
        if (attendance == null) {
            throw new IllegalArgumentException(
                "Error! Input attendance is null.");
        }

        Member member = null;
        Practice practice = null;

        if (attendance.getMemberKey() == null) {
            throw new IllegalArgumentException(
                "Error! Input Attendance.memberKey is null.");
        } else if ((member = memberSvc.searchFromKey(attendance.getMemberKey())) == null) {
            throw new IllegalArgumentException(
                "Error! Member related to attendance is null.");
        }

        if (attendance.getPracticeKey() == null) {
            throw new IllegalArgumentException(
                "Error! Input Attendance.practicerKey is null.");
        } else if ((practice =
            practiceSvc.searchFromKey(attendance.getPracticeKey())) == null) {
            throw new IllegalArgumentException(
                "Error! Practice related to attendance is null.");
        }

        // Key, MemberRef, PracticeRef の設定
        attendance.setKey(generateKey(attendance));
        if (attendance.getMemberRef().getModel() == null) {
            attendance.getMemberRef().setModel(member);
        }
        if (attendance.getPracticeRef().getModel() == null) {
            attendance.getPracticeRef().setModel(practice);
        }

        // Inputの設定
        attendance.setInputFlg(true);

        return attendance;
    }

    /**
     * 出欠情報を１件登録します
     * 
     * @param attendance
     *            登録対象の出欠情報
     * @return 登録された出欠情報
     */
    public Attendance regist(Attendance attendance)
            throws IllegalArgumentException {
        attendance = preRegist(attendance);
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(attendance);
        gtx.commit();
        return attendance;
    }

    /**
     * RequestMapから出欠情報を登録します
     * 
     * @param input
     *            RequestMap
     * @return 登録された出欠情報
     * @throws IllegalArgumentException
     */
    public Attendance regist(Map<String, Object> input)
            throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("Error! Input input is null.");
        } else {
            Attendance attendance = new Attendance();
            BeanUtil.copy(input, attendance);
            return regist(attendance);
        }
    }

    /**
     * 出欠情報を複数件登録します １件でも登録に失敗した場合は、途中に登録したものも全て削除します。（Atomic保持のため）
     * 
     * @param attendanceList
     *            登録対象の出欠情報のリスト
     * @return 登録された出欠情報のリスト
     */
    public List<Attendance> registList(List<Attendance> attendanceList)
            throws IllegalArgumentException {
        if (attendanceList == null || attendanceList.size() == 0) {
            throw new IllegalArgumentException(
                "Error! Input attendanceList is null.");
        } else {
            List<Attendance> constructedList = new ArrayList<Attendance>();
            for (Attendance attendance : attendanceList) {
                constructedList.add(preRegist(attendance));
            }
            GlobalTransaction gtx = Datastore.beginGlobalTransaction();
            gtx.put(constructedList);
            gtx.commit();
            return constructedList;
        }
    }

    /**
     * 出欠情報を更新します
     * 
     * @param key
     *            更新対象出欠のKey
     * @param attendance
     *            出欠入力情報
     * @param racital
     *            備考記載情報
     * @return
     * @throws IllegalArgumentException
     */
    public Attendance update(Key key, int attendance, String racital)
            throws IllegalArgumentException {

        if (key == null) {
            throw new IllegalArgumentException("Error! Input key is null.");
        }

        Attendance updateAttendance = searchFromKey(key);
        updateAttendance.setAttendance(attendance);
        updateAttendance.setRacital(racital);

        return update(updateAttendance);
    }

    /**
     * 出欠情報を１件更新します 更新対象が存在しない場合はエラーを発生します。
     * 
     * @param attendance
     *            更新対象の出欠情報
     * @return
     */
    public Attendance update(Attendance attendance)
            throws IllegalArgumentException {
        if (attendance == null) {
            throw new IllegalArgumentException(
                "Error! Input attendance is null.");
        } else if (searchFromKey(attendance.getKey()) == null) {
            throw new IllegalArgumentException(
                "Error! Target attendance does not exist. Key is "
                    + attendance.getKey());
        }
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(attendance);
        gtx.commit();
        return attendance;
    }

    /**
     * 出欠情報を複数件更新します 更新対象が存在しない場合はエラーを発生します。
     * 
     * @param attendanceList
     *            更新対象の出欠情報のリスト
     * @return 更新された出欠情報のリスト
     */
    public List<Attendance> updateList(List<Attendance> attendanceList) {
        if (attendanceList == null || attendanceList.size() == 0) {
            throw new IllegalArgumentException(
                "Error! Input attendanceList is null.");
        } else {
            // 不正な出欠情報がある場合は更新しない
            for (Attendance attendance : attendanceList) {
                if (attendance == null) {
                    throw new IllegalArgumentException(
                        "Error! Input attendance is null.");
                } else if (searchFromKey(attendance.getKey()) == null) {
                    throw new IllegalArgumentException(
                        "Error! Target attendance does not exist. Key is "
                            + attendance.getKey());
                }
            }
            // 実際の更新処理
            return registList(attendanceList);
        }
    }

    /**
     * データが存在する場合は更新、存在しない場合は新規登録を行います。 備考の更新は対象外です。
     * 
     * @param attendance
     * @param memberKey
     * @param practiceKey
     * @return
     */
    public Attendance updateIfNotExistInsert(int attendance, Key memberKey,
            Key practiceKey) {

        if (memberKey == null) {
            throw new IllegalArgumentException(
                "Error! Input memberKey is null.");
        } else if (practiceKey == null) {
            throw new IllegalArgumentException(
                "Error! Input practiceKey is null.");
        }

        Attendance attendanceEntity =
            searchFromMemberKeyAndPracticeKey(memberKey, practiceKey);

        if (attendanceEntity == null) {
            attendanceEntity =
                getInitalizedAttendance(
                    memberSvc.searchFromKey(memberKey),
                    practiceSvc.searchFromKey(practiceKey));
            attendanceEntity.setAttendance(attendance);
            regist(attendanceEntity);
        } else {
            attendanceEntity.setAttendance(attendance);
            update(attendanceEntity);
        }

        return attendanceEntity;
    }

    /**
     * データが存在する場合は更新、存在しない場合は新規登録を行います。
     * 
     * @param attendance
     * @param memberKey
     * @param practiceKey
     * @return
     */
    public Attendance updateIfNotExistInsert(int attendance, String racital,
            Key memberKey, Key practiceKey) {

        if (memberKey == null) {
            throw new IllegalArgumentException(
                "Error! Input memberKey is null.");
        } else if (practiceKey == null) {
            throw new IllegalArgumentException(
                "Error! Input practiceKey is null.");
        }

        Attendance attendanceEntity =
            searchFromMemberKeyAndPracticeKey(memberKey, practiceKey);

        if (attendanceEntity == null) {
            attendanceEntity =
                getInitalizedAttendance(
                    memberSvc.searchFromKey(memberKey),
                    practiceSvc.searchFromKey(practiceKey));
            attendanceEntity.setAttendance(attendance);
            attendanceEntity.setRacital(racital);
            regist(attendanceEntity);
        } else {
            attendanceEntity.setAttendance(attendance);
            attendanceEntity.setAttendance(attendance);
            attendanceEntity.setRacital(racital);
            update(attendanceEntity);
        }

        return attendanceEntity;
    }

    /**
     * 出欠情報を１件削除します。
     * 
     * @param attendance
     *            削除対象の出欠情報
     * @return 削除の結果（成功:true, 失敗:false）
     */
    public boolean delete(Attendance attendance) {
        if (attendance == null) {
            return false;
        } else {
            GlobalTransaction gtx = Datastore.beginGlobalTransaction();
            gtx.delete(attendance.getKey());
            gtx.commit();
            return true;
        }
    }

    /**
     * 指定日時の出席情報を全て削除します。
     * 
     * @param date
     * @param gtx
     */
    public void deleteList(Date date, GlobalTransaction gtx) {
        List<Attendance> attendanceList = searchFromPracticeDate(date);
        if (attendanceList != null && attendanceList.size() != 0) {
            deleteList(attendanceList, gtx);
        }
    }

    /**
     * 出欠情報を複数件削除します
     * 
     * @param attendanceList
     *            削除対象の出欠情報のリスト
     * @throws IllegalArgumentException
     */
    public void deleteList(List<Attendance> attendanceList)
            throws IllegalArgumentException {
        deleteList(attendanceList, null);
    }

    /**
     * 出欠情報を複数件削除します
     * 
     * @param attendanceList
     *            削除対象の出欠情報のリスト
     * @throws IllegalArgumentException
     */
    public void deleteList(List<Attendance> attendanceList,
            GlobalTransaction gtx) throws IllegalArgumentException {
        if (attendanceList == null || attendanceList.size() == 0) {
            throw new IllegalArgumentException(
                "Error! Input attendanceList is null.");
        } else {
            Set<Key> keySet = new HashSet<Key>();
            for (Attendance attendance : attendanceList) {
                keySet.add(attendance.getKey());
            }
            if (gtx == null) {
                gtx = Datastore.beginGlobalTransaction();
                gtx.delete(keySet);
                gtx.commit();
            } else {
                gtx.delete(keySet);
            }
        }
    }

    /**
     * Keyで出欠情報を１件取得します
     * 
     * @param key
     * @return
     */
    public Attendance searchFromKey(Key key) {
        if (key == null) {
            return null;
        } else {
            return Datastore.query(t).filter(t.key.equal(key)).asSingle();
        }
    }

    /**
     * メンバーが入力した出欠情報を検索します。
     * 
     * @param memberId
     * @return 出欠情報のリスト
     */
    public List<Attendance> searchFromMemberId(String memberId) {

        if (memberId == null) {
            return null;
        }

        Member member = memberSvc.searchFromId(memberId);
        if (member == null) {
            return null;
        } else {
            return Datastore
                .query(t)
                .filter(t.memberKey.equal(member.getKey()))
                .asList();
        }
    }

    /**
     * 特定の練習日の出欠情報を検索します。
     * 
     * @param practiceDate
     *            練習日の開始日時
     * @return 出欠情報のリスト
     */
    public List<Attendance> searchFromPracticeDate(Date practiceDate) {

        if (practiceDate == null) {
            return null;
        }

        Practice practice = practiceSvc.searchFromStartDateTime(practiceDate);
        if (practice == null) {
            return null;
        } else {
            return Datastore
                .query(t)
                .filter(t.practiceKey.equal(practice.getKey()))
                .asList();
        }
    }

    /**
     * メンバーと練習日で出欠情報を１件検索します
     * 
     * @param memberId
     *            メンバーID
     * @param practiceDate
     *            練習日の開始日時
     * @return 出欠情報
     */
    public Attendance searchFromMemberIdAndPracticeDate(String memberId,
            Date practiceDate) {

        if (memberId == null || practiceDate == null) {
            return null;
        }

        Member member = memberSvc.searchFromId(memberId);
        Practice practice = practiceSvc.searchFromStartDateTime(practiceDate);
        if (member == null || practice == null) {
            return null;
        } else {
            return Datastore
                .query(t)
                .filter(
                    t.memberKey.equal(member.getKey()),
                    t.practiceKey.equal(practice.getKey()))
                .asSingle();
        }
    }

    /**
     * メンバーと練習日で出欠情報を１件検索します
     * 
     * @param memberId
     *            メンバーID
     * @param practiceDate
     *            練習日の開始日時
     * @return 出欠情報
     */
    public Attendance searchFromMemberKeyAndPracticeKey(Key memberKey,
            Key practiceKey) {

        if (memberKey == null || practiceKey == null) {
            return null;
        }

        return Datastore
            .query(t)
            .filter(
                t.memberKey.equal(memberKey),
                t.practiceKey.equal(practiceKey))
            .asSingle();
    }

    /**
     * メンバーと日程から、出欠情報を取得します。（出欠計算用）
     * 
     * @param memberKey
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<Attendance> searchFromMemberKeyAndDateSpanForCalc(
            Key memberKey, Date fromDate, Date toDate) {

        PracticeService practiceSvc = new PracticeService();

        List<Key> practiceKeyList =
            practiceSvc.searchKeyListFromDateSpanForCalc(fromDate, toDate);

        if(practiceKeyList == null || practiceKeyList.isEmpty()){
            return null;
        }
        
        return Datastore
            .query(t)
            .filter(t.memberKey.equal(memberKey))
            .filter(t.practiceKey.in(practiceKeyList))
            .asList();
    }
    
    /**
     * メンバーと日程から、出欠情報を取得します。（出欠計算用）
     * 
     * @param memberKey
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<Attendance> searchFromMemberKeyAndPracticeKey(
            Key memberKey, List<Key> practiceKeyList) {

        if(memberKey == null){
            return null;
        }
        
        if(practiceKeyList == null || practiceKeyList.isEmpty()){
            return null;
        }
        
        return Datastore
            .query(t)
            .filter(t.memberKey.equal(memberKey))
            .filter(t.practiceKey.in(practiceKeyList))
            .asList();
    }

    /**
     * Attendance の key を生成します。 Key is intended to be
     * "Attendance("Member(hoge)/Practice("hoge")")"
     * 
     * @param memberKey
     * @param practiceKey
     * @return
     */
    protected Key generateKey(Key memberKey, Key practiceKey) {
        return Datastore.createKey(memberKey, t, practiceKey.toString());
    }

    /**
     * Attendance の key を生成します。 Key is intended to be
     * "Attendance("Member(hoge)/Practice("hoge")")"
     * 
     * @param attendance
     * @return
     */
    protected Key generateKey(Attendance attendance) {
        if (attendance == null
            || attendance.getMemberKey() == null
            || attendance.getPracticeKey() == null) {
            return null;
        } else {
            return generateKey(
                attendance.getMemberKey(),
                attendance.getPracticeKey());
        }
    }

    /**
     * 指定された日付の月末日を取得します
     * 
     * @param date
     * @return
     */
    protected Date getEndOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * メンバー情報と練習情報から初期化された出欠情報を取得します。
     * 
     * @param member
     * @param practice
     * @return
     */
    public Attendance getInitalizedAttendance(Member member, Practice practice) {
        Attendance attendance = new Attendance();

        attendance.setAttendance(0);
        attendance.setMemberKey(member.getKey());
        attendance.setPracticeKey(practice.getKey());
        attendance.setFinished(false);
        attendance.setInputFlg(false);
        attendance.getMemberRef().setModel(member);
        attendance.getPracticeRef().setModel(practice);
        return attendance;
    }

    /**
     * 練習情報の入力済みフラグをオフにします
     * 
     * @param key
     * @return
     */
    public Attendance updateInputFlgToFalse(Key key)
            throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Error! Input key is null.");
        }
        Attendance attendance = searchFromKey(key);
        attendance.setInputFlg(false);
        return update(attendance);
    }
}
