package slim3.controller.attend.manage.practice.regist;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.DateUtil;

import slim3.model.Practice;
import slim3.service.PracticeService;

public class IndexController extends Controller {

    private PracticeService practiceService = new PracticeService();

    @Override
    public Navigation run() throws Exception {

        Practice practice = null;
        if (asKey("key") != null) {
            practice = practiceService.searchFromKey(asKey("key"));
        }

        // requestScope�ɗ��K��񂪑��݂���ꍇ�icopy��z��j
        if (practice != null) {

            requestScope("startTime", DateUtil.toString(
                practice.getStartDate(),
                practiceService.TIME_PATTERN));
            requestScope("endTime", DateUtil.toString(
                practice.getEndDate(),
                practiceService.TIME_PATTERN));
            requestScope("practicePlace", practice.getPracticePlace());
            requestScope("gatheringTime", DateUtil.toString(
                practice.getGatheringDate(),
                practiceService.TIME_PATTERN));
            requestScope("gatheringPoint", practice.getGatheringPoint());
            requestScope("racital", practice.getRecital());
            requestScope("needSendMail", practice.isNeedSendMail());
            requestScope("needCalc", practice.isNeedCalc());

            // ���݂��Ȃ��ꍇ�i�ʏ�̎g�p��z��A�v���ɏ]���ď����l����͂���B�j
        } else {

            // �����\��
            requestScope("startTime", "13:00");
            requestScope("endTime", "17:00");
            requestScope("gatheringTime", "12:50");
            requestScope("gatheringPoint", "���n");
            requestScope("needSendMail", true);
            requestScope("needCalc", true);

        }

        return forward("index.jsp");
    }
}
