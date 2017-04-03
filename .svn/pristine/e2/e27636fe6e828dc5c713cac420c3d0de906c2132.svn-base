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

        // requestScopeに練習情報が存在する場合（copyを想定）
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

            // 存在しない場合（通常の使用を想定、要件に従って初期値を入力する。）
        } else {

            // 初期表示
            requestScope("startTime", "13:00");
            requestScope("endTime", "17:00");
            requestScope("gatheringTime", "12:50");
            requestScope("gatheringPoint", "現地");
            requestScope("needSendMail", true);
            requestScope("needCalc", true);

        }

        return forward("index.jsp");
    }
}
