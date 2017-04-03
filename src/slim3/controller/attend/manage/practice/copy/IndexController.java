package slim3.controller.attend.manage.practice.copy;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.model.Practice;
import slim3.service.PracticeService;

public class IndexController extends Controller {

    private PracticeService practiceService = new PracticeService();    
    
    @Override
    public Navigation run() throws Exception {

        Practice practice = practiceService.searchFromKey(asKey("key"));
        requestScope("practice", practice);
        return forward("index.jsp");

    }
}
