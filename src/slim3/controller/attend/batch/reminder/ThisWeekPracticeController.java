package slim3.controller.attend.batch.reminder;

import java.util.Date;
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

public class ThisWeekPracticeController extends Controller {

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
        List<Practice> practiceList = getThisWeekPracticeList();
        
        // ���K������ꍇ�A���[�����쐬�����M����B
        if (practiceList != null && practiceList.size() > 0) {
            sendMail(practiceList);
            logger.info("���[���𑗐M���܂����B�Ώۗ��K���F" + practiceList.size());
        } else {
            logger.info("�Y��������K���Ȃ����߃��[���𑗐M���܂���ł����B");
        }

        return null;
    }

    /**
     * ���[�����M���K�v�ȗ��K���X�g���擾���܂��B���ԁF�V����܂ŁB
     * 
     * @return
     */
    private List<Practice> getThisWeekPracticeList() {

        Date fromDate = DateUtil.clearTimePart(AttendDateUtil.getCurrentDate());
        Date toDate = AttendDateUtil.addDayOfMonth(fromDate, 7); // ��T�Ԍ�
        return practiceSvc.searchListForMail(fromDate, toDate);
    }

    /**
     * ���[���𑗐M���܂��B
     * 
     * @param practiceList
     */
    private void sendMail(List<Practice> practiceList) {
        String subject = makeSubject();
        String body = makeBody(practiceList);
        mailSendSvc.sendMailToML(subject, body);
    }

    /**
     * ���[���^�C�g�����쐬���܂��B
     * 
     * @return
     */
    private String makeSubject() {
        return "���T�̗��K�̂��m�点�ł��B";
    }

    /**
     * ���[���{�����쐬���܂��B
     * 
     * @param practice
     * @return
     */
    private String makeBody(List<Practice> practiceList) {

        String headerStr = "���T�̗��K�̂��m�点�ł��B���T�͈ȉ��̒ʂ���K������܂��B\n�����̏o�����m�F���A�Ⴄ�ꍇ��MEKAZAWA�ŏC�����ĉ������B\n\n";
        String contentStr = makeContent(practiceList);
        String footerStr = "���K��������K�����ɂ��Ă͈ȉ��̃T�C�g���������������B\nhttps://sites.google.com/site/abeinuhigashi/renshunisshi\n\n" + "���̃��[����MEKAZAWA��莩���z�M����Ă��܂��B�����������ȓ_������΃}�l�[�W���ɘA���������B";

        return headerStr + contentStr + footerStr;
    }

    /**
     * ���K��񂩂當�ʂ��쐬���܂��B
     * 
     * @param practiceList
     * @return
     */
    private String makeContent(List<Practice> practiceList) {

        StringBuffer bufStr = new StringBuffer();

        for (Practice practice : practiceList) {
            bufStr.append(makePracticeStr(practice)
                + "\n"
                + makeAttendanceStr(practice)
                + "\n\n");
        }

        return bufStr.toString();
    }

    /**
     * ���[�����ʂ̂������K���Ɋւ�镶�ʂ��쐬���܂��B
     * 
     * @param practice
     * @return
     */
    private String makePracticeStr(Practice practice) {
        String startDateStr =
            DateUtil.toString(practice.getStartDate(), "MM��dd�� HH:mm�`");
        String practicePlace = practice.getPracticePlace();

        if (StringUtil.isEmpty(practicePlace)) {
            practicePlace = "����";
        }
        return "�� " + startDateStr + "��" + practicePlace;
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

        return "�o�ȗ\��҂́A"
            + sopAttendStr.toString()
            + altoAttendStr.toString()
            + tenAttendStr.toString()
            + bassAttendStr.toString()
            + "�ł��B";
    }

}
