package slim3.controller.attend.manage.attendance.date.search;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.model.Practice;
import slim3.service.PracticeService;
import slim3.util.AttendDateUtil;

public class IndexController extends Controller {

    private static PracticeService svc = new PracticeService();

    @Override
    public Navigation run() throws Exception {
        List<Practice> practiceList = svc.search(AttendDateUtil.getCurrentDate(), 50);
        requestScope("practiceList", practiceList);
        return forward("index.jsp");
    }
}
