package slim3.controller.attend.manage.practice.search;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.model.Practice;
import slim3.service.PracticeService;

public class IndexController extends Controller {
    
    private static PracticeService svc = new PracticeService();

    @Override
    public Navigation run() throws Exception {
        List<Practice> practiceList = svc.search(getCurrentDate(), 50);
        requestScope("practiceList", practiceList);
        return forward("index.jsp");
    }
    
    /**
     * requestParameters から現在の年月を取得します
     * requestParameters に存在しない場合はシステム日付から取得します。（JST）
     * 
     * @return
     */
    private Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 9);
        return cal.getTime();
    }
}
