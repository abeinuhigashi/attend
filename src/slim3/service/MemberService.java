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
     * ID��Password�Ń��[�U�[�����擾���܂�
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
     * Key�Ń��O�C�����܂��B
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
     * �Ώ�Member�̃��O�C���L�[�i�N�b�L�[�p�j���擾����
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
     * Member����l�o�^����B �h�c���Ȃ��ꍇ�A�܂������h�c�����݂���ꍇ�̓G���[
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
     * Member����l�o�^���܂��B
     * 
     * @param member
     * @return
     */
    public Member regist(Member member) {

        if (member == null) {
            throw new IllegalArgumentException("Error! Input member is null.");
        }

        // �Ј��ԍ����Ȃ���΃G���[
        if (member.getId() == null) {
            throw new IllegalArgumentException("Error! Input id is null.");
        }
        // �Ј������݂���Γo�^���s
        if (searchFromId(member.getId()) != null) {
            throw new IllegalArgumentException(
                "Error! Input id already exists.");
        }

        // �����o�^���Ƀf�t�H���g�œ��鍀��
        member.setSuspended(false);
        member.setNotUsedLongTime(false);
        member.setMailUse(false);
        member.setMailError(false);

        // �f�[�^�o�^
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(member);
        gtx.commit();
        return member;
    }

    /**
     * Key����Member���������܂��B
     * 
     * @param key
     * @return Member
     */
    public Member searchFromKey(Key key) {
        return Datastore.query(t).filter(t.key.equal(key)).asSingle();
    }

    /**
     * ID��Member���������܂�
     * 
     * @param id
     * @return Member
     */
    public Member searchFromId(String id) {
        return Datastore.query(t).filter(t.id.equal(id)).asSingle();
    }

    /**
     * Member��ID��Password�Ō������܂�
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
     * Member��ID�Ő����v�������܂�
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
     * ������ȑO�Ƀ��O�C������Member�ꗗ���擾���܂� �����������͎҂�􂢏o���̂Ɏg�p����z��
     * ���O�C��������null��Member�͒��o�ΏۊO
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
     * �SMember���擾�擾���܂��iID�Ń\�[�g�j
     * 
     * @return �SMember
     */
    public List<Member> getAll() {
        return Datastore.query(t).sort(t.id.asc).asList();
    }

    /**
     * �x�c���Ă��Ȃ��A���A���������͂łȂ������o�[��Key���擾���܂��B SATB�̏��Ƀ\�[�g�B
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
     * Member�����X�V���܂�
     * 
     * @param member
     *            �X�V�ΏێЈ����
     * @return �X�V��Ј����
     * @throws IllegalArgumentException
     *             �X�V�ΏێЈ������݂��Ȃ��ꍇ�ɔ���
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
     * Member����l�X�V���܂��B
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

        // �Ј��ԍ����Ȃ���΃G���[
        if (member.getId() == null) {
            throw new IllegalArgumentException("Error! Input id is null.");
        }
        // �Ј������݂��Ȃ���΍X�V���s
        if (searchFromKey(member.getKey()) == null) {
            throw new IllegalArgumentException(
                "Error! Input id already exists.");
        }

        // �f�[�^�o�^
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(member);
        gtx.commit();
        return member;
    }

    /**
     * Member���폜���܂�
     * 
     * @param id
     *            �폜�Ώ�ID
     * @return ������true��Ԃ�
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
     * �ŏI���O�C�����̍X�V���s���܂��B
     * 
     * @param member
     * @return
     */
    private Member updateLastLoginInfo(Member member) {
        
        if(member == null){
            return null;
        }
        
        // �ŏI���O�C���������X�V
        member.setLastLoginDateTime(AttendDateUtil.getCurrentDate());

        // ���������͎҂̏ꍇ�A��������B
        if (member.isNotUsedLongTime()) {
            member.setNotUsedLongTime(false);
            String subject = "���������͎҉����̂��m�点";
            String msg =
                member.getLastName()
                    + " "
                    + member.getFirstName()
                    + "���񂪃��O�C���������߁A���������͎҂��珜�O���܂����B";
            mailSendSvc.sendMailToSystemAdmin(subject, msg);
        }

        return update(member);
    }
}
