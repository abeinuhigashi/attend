package slim3.controller.attend.manage.practice.delete;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import slim3.service.PracticeService;

import com.google.appengine.api.datastore.Key;

public class SubmitController extends Controller {

    private PracticeService practiceService = new PracticeService();

    @Override
    public Navigation run() throws Exception {

        if (asString("cancel") != null) {
            return forward("/attend/manage/practice/search/");
        } else if (asString("submit") != null) {

            Key key = asKey("key");
            if (key != null) {
                practiceService.delete(key);
                return forward("submit.jsp");
            } else {
                throw new RuntimeException("削除キーがないっす。");
            }
        }
        throw new RuntimeException("予期してないよ。");        
    }
}
