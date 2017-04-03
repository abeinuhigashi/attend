package slim3.util;

import java.util.Calendar;
import java.util.Date;

public class AttendDateUtil {

    /**
     * ���t���V�X�e�����t���ߋ����ǂ������肵�܂��B �ߋ��ł����true, �����ł����false, ������true�i�ߋ������j
     * 
     * @param targetDate
     * @return
     */
    public static boolean checkPast(Date targetDate) {

        Date practiceDate = targetDate;
        Calendar practiceCalendar = Calendar.getInstance();
        practiceCalendar.setTimeInMillis(practiceDate.getTime());

        int practiceYear = practiceCalendar.get(Calendar.YEAR);
        int practiceMonth = practiceCalendar.get(Calendar.MONTH);
        int practiceDay = practiceCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();

        int sysYear = calendar.get(Calendar.YEAR);
        int sysMonth = calendar.get(Calendar.MONTH);
        int sysDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (practiceYear < sysYear) {
            // �Ώۓ��̔N�x���ߋ��̏ꍇ
            return true;
        } else if (practiceYear > sysYear) {
            // �Ώۓ��̔N�x�������̏ꍇ
            return false;
        }

        // �ȉ��A�Ώۓ��̔N�x���V�X�e�����t�̔N�x�Ɠ����ꍇ
        if (practiceMonth < sysMonth) {
            // �Ώۓ��̌����ߋ��̏ꍇ
            return true;
        } else if (practiceMonth > sysMonth) {
            // �Ώۓ��̌��������̏ꍇ
            return false;
        }

        // �ȉ��A�Ώۓ��̌����V�X�e�����t�̌��Ɠ����ꍇ
        if (practiceDay <= sysDay) {
            // �����̏ꍇ���ߋ��Ɋ܂�
            return true;
        }

        return false;
    }

    /**
     * ���ݓ������擾���܂��B�iJST�j
     * 
     * @return ���ݓ���
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 9);
        return cal.getTime();
    }

    /**
     * �����̂P�����擾���܂��B
     * 
     * @param baseDate
     * @return
     */
    public static Date getFirstDayOfNextMonth(Date baseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    /**
     * �O���̂P�����擾���܂��B
     * 
     * @param baseDate
     * @return
     */
    public static Date getFirstDayOfBeforeMonth(Date baseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * ���X���̂P�����擾���܂�
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfTwiceNextMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 2); // ���X��
        cal.set(Calendar.DAY_OF_MONTH, 1); // ���X���̂P��
        cal.add(Calendar.DAY_OF_MONTH, -1); // �O��

        return cal.getTime();
    }

    /**
     * ���P�ʂŉ��Z���܂��B
     * 
     * @param date
     *            ���Z�Ώۓ��t
     * @param num
     *            ���Z�����i�}�C�i�X�Ȃ�ߋ����ցj
     * @return
     */
    public static Date addDayOfMonth(Date date, int num) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, num);

        return cal.getTime();
    }

    /**
     * ���P�ʂŉ��Z���܂��B
     * 
     * @param date
     * @param num
     * @return
     */
    public static Date addMonth(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);

        return cal.getTime();
    }
    
    /**
     * ���P�ʂŉ��Z���܂��B
     * 
     * @param date
     * @param num
     * @return
     */
    public static Date addDay(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, num);

        return cal.getTime();
    }
}
