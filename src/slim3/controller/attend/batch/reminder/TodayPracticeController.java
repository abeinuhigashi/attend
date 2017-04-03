package slim3.controller.attend.batch.reminder;

import java.util.List;
import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;
import org.slim3.util.StringUtil;

import slim3.constants.Constants;
import slim3.model.Attendance;
import slim3.model.Member;
import slim3.model.Practice;
import slim3.service.AttendanceService;
import slim3.service.MailSendService;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

public class TodayPracticeController extends Controller {

    private static PracticeService practiceSvc = new PracticeService();
    private static AttendanceService attendanceSvc = new AttendanceService();
    private static MailSendService mailSendSvc = new MailSendService();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Navigation run() throws Exception {

        logger.info(this.getClass().getName()
            + " start... at "
            + AttendDateUtil.getCurrentDate());

        // �{�����K�����邩�ǂ����m�F����
        List<Practice> practiceList = getTodayPracticeList();

        // ���K������ꍇ�A���[�����쐬�����M����B
        if (practiceList != null && practiceList.size() > 0) {
            sendMail(practiceList.get(0));
            logger
                .info("���[���𑗐M���܂����B�Ώۗ��K�F" + practiceList.get(0).getStartDate());
        } else {
            logger.info("�Y��������K���Ȃ����߃��[���𑗐M���܂���ł����B");
        }

        return null;
    }

    /**
     * �{���̗��K�ꗗ���擾����B
     * 
     * @return
     */
    private List<Practice> getTodayPracticeList() {
        return practiceSvc.searchForMail(AttendDateUtil.getCurrentDate());
    }

    /**
     * ���[���𑗐M���܂��B
     * 
     * @param practice
     */
    private void sendMail(Practice practice) {

        // �^�C�g���̍쐬
        String subject = makeSubject();

        // �{���̍쐬
        String body = makeBody(practice);

        // ���[���̑��M
        mailSendSvc.sendMailToML(subject, body);

    }

    /**
     * ���[���^�C�g�����쐬���܂��B
     * 
     * @return
     */
    private String makeSubject() {
        return "�{�����K������܂��B";
    }

    /**
     * ���[���{�����쐬���܂��B
     * 
     * @param practice
     * @return
     */
    private String makeBody(Practice practice) {

        // ���K���Ɋւ��镶�ʂ��쐬
        String practiceStr = makePracticeStr(practice);

        // �W�����Ɋւ��镶�ʂ��쐬
        String gatheringStr = makeGatheringStr(practice);

        // �o�ȏ��Ɋւ��镶�ʂ��쐬
        String attendanceStr = makeAttendanceStr(practice);

        return "�{���͗��K���ł��B\n"
            + practiceStr
            + "\n"
            + gatheringStr
            + "\n"
            + attendanceStr
            + "\n\n"
            + "���K��������K�����ɂ��Ă͈ȉ��̃T�C�g���������������B\nhttps://sites.google.com/site/abeinuhigashi/renshunisshi\n\n���̃��[����MEKAZAWA��莩�����M����Ă��܂��B�����������ȓ_������΃}�l�[�W���ɘA���������B";
    }

    /**
     * ���[�����ʂ̂����o�Ȏ҂Ɋւ�镶�ʂ��쐬���܂��B
     * 
     * @param practice
     * @return
     */
    private String makeAttendanceStr(Practice practice) {
        List<Attendance> attendanceList =
            attendanceSvc.searchFromPracticeDate(practice.getStartDate());

        StringBuffer sopAttendStr = new StringBuffer();
        StringBuffer altoAttendStr = new StringBuffer();
        StringBuffer tenAttendStr = new StringBuffer();
        StringBuffer bassAttendStr = new StringBuffer();

        for (Attendance attendance : attendanceList) {
            if (attendance.getAttendance() == Constants.ATTENDANCE_PRESENCE
                || attendance.getAttendance() == Constants.ATTENDANCE_TARDINESS
                || attendance.getAttendance() == Constants.ATTENDANCE_EARLYLEAVE) {
                Member member = attendance.getMemberRef().getModel();
                String nameStr = member.getNickName();
                if(StringUtil.isEmpty(nameStr)){
                    nameStr = member.getLastName();
                }
                if (attendance.getAttendance() == Constants.ATTENDANCE_TARDINESS) {
                    nameStr = nameStr + "�i�x���j";
                } else if (attendance.getAttendance() == Constants.ATTENDANCE_EARLYLEAVE) {
                    nameStr = nameStr + "�i���ށj";
                }

                // �p�[�g�Ń\�[�g���ĕ\�����邽�߂ɂ��̂悤�ɂ���B
                if (Constants.PART_SOPRANO.equals(member.getPart())) {
                    sopAttendStr.append(nameStr + "�A");
                } else if (Constants.PART_ALTO.equals(member.getPart())) {
                    altoAttendStr.append(nameStr + "�A");
                } else if (Constants.PART_TENOR.equals(member.getPart())) {
                    tenAttendStr.append(nameStr + "�A");
                } else if (Constants.PART_BASS.equals(member.getPart())) {
                    bassAttendStr.append(nameStr + "�A");
                }
            }
        }

        return "�o�Ȏ҂́A"
            + sopAttendStr.toString()
            + altoAttendStr.toString()
            + tenAttendStr.toString()
            + bassAttendStr.toString()
            + "�ł��B";
    }

    /**
     * ���[�����ʂ̂����W�����Ɋւ�镶�ʂ��쐬���܂��B
     * 
     * @param practice
     * @return
     */
    private String makeGatheringStr(Practice practice) {

        String gatheringPointStr = practice.getGatheringPoint();
        String gatheringDateStr =
            DateUtil.toString(practice.getGatheringDate(), "HH:mm");

        String rtnStr = null;
        if (StringUtil.isEmpty(gatheringPointStr)
            || StringUtil.isEmpty(gatheringDateStr)) {
            rtnStr = "�W�����͐ݒ肳��Ă��܂���B";
        } else {
            rtnStr = gatheringPointStr + "��" + gatheringDateStr + "�ɏW�����ĉ������B";
        }
        return rtnStr;
    }

    /**
     * ���[�����ʂ̂������K���Ɋւ�镶�ʂ��쐬���܂��B
     * 
     * @param practice
     * @return
     */
    private String makePracticeStr(Practice practice) {
        String startDateStr =
            DateUtil.toString(practice.getStartDate(), "HH:mm");
        String practicePlace = practice.getPracticePlace();

        if (StringUtil.isEmpty(practicePlace)) {
            practicePlace = "����";
        }
        return "�J�n���Ԃ�" + startDateStr + "�B�ꏊ��" + practicePlace + "�ł��B";
    }
}
