package slim3.controller.attend.manage.practice.update;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.DateUtil;

import slim3.meta.PracticeMeta;
import slim3.model.Practice;
import slim3.service.PracticeService;

public class IndexController extends Controller {
    
    private PracticeMeta meta = new PracticeMeta();
    private PracticeService svc = new PracticeService();

    @Override
    public Navigation run() throws Exception {

        // 入力チェック
        if(!validate()){
            errors.put("selectError", "1つ選んで下さい。");
            return forward("/attend/manage/practice/search/");   // index.jspだと違うところに飛んでしまう
        }
        
        // ボタンによって遷移先を振り分ける（updateの場合は本コントローラにて処理。）
        if(asString("update") != null) {
            // 更新ボタンが押された場合
            Practice practice = svc.searchFromKey(asKey("key"));
            
            request.setAttribute("practiceDate", DateUtil.toString(practice.getStartDate(), DateUtil.ISO_DATE_PATTERN));
            request.setAttribute("startTime", DateUtil.toString(practice.getStartDate(), "HH:mm"));
            request.setAttribute("endTime", DateUtil.toString(practice.getEndDate(), "HH:mm"));
            request.setAttribute("practicePlace", practice.getPracticePlace());
            request.setAttribute("gatheringTime", DateUtil.toString(practice.getGatheringDate(), "HH:mm"));
            request.setAttribute("gatheringPoint", practice.getGatheringPoint());
            request.setAttribute("racital", practice.getRecital());
            request.setAttribute("needSendMail", practice.isNeedSendMail());
            request.setAttribute("needCalc", practice.isNeedCalc());
            request.setAttribute("key", practice.getKey());
            
            return forward("index.jsp");
            
        } else if(asString("delete") != null) {
            // 削除ボタンが押された場合
            return forward("/attend/manage/practice/delete/");
        } else if(asString("copy") != null) {
            // コピーボタンが押された場合
            return forward("/attend/manage/practice/regist/");
        }
        
        // TODO 想定外のためエラー画面に遷移する必要がある
        return forward("index.jsp");
    }
    
    /**
     * 画面入力チェックを行ないます。
     * 
     * @return チェック結果
     */
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add(meta.key, v.required());
        return v.validate();
    }
}
