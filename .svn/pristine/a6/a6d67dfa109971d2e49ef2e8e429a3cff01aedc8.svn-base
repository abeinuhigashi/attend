package slim3.controller.attend.manage.member.regist;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.ApplicationMessage;
import org.slim3.util.RequestMap;

import slim3.meta.MemberMeta;
import slim3.model.Member;
import slim3.service.MemberAuthService;
import slim3.service.MemberService;

public class SubmitController extends Controller {

    private MemberService svc = new MemberService();
    private MemberMeta meta = new MemberMeta();
    private MemberAuthService authSvc = new MemberAuthService();

    @Override
    public Navigation run() throws Exception {

        if(!validate()){
            return forward(basePath);
        }

        try {
            Member member = svc.regist(new RequestMap(request));
            authSvc.registAsInitial(member);

            requestScope("member", member);
            return forward("submit.jsp");
        } catch (IllegalArgumentException e){
            errors.put("cannotRegist", ApplicationMessage.get("regist.error"));
            return forward(basePath);
        }

    }

    /**
     * 画面入力チェックを行ないます。
     *
     * @return チェック結果
     */
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add(meta.id, v.required(), v.regexp("[A-Za-z0-9]+", "英字だけでお願い！"));
        v.add(meta.password, v.required());
        v.add(meta.firstName, v.required());
        v.add(meta.lastName, v.required());
        return v.validate();
    }
}