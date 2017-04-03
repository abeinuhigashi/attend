package slim3.controller.attend.changePassword;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

import slim3.model.Member;
import slim3.service.MemberService;

public class SubmitController extends Controller {
    
    private MemberService memberSvc = new MemberService();

    @Override
    public Navigation run() throws Exception {
        
        if(!validate() || !customValidate()){
            return forward(basePath);
        }
        
        Member member = memberSvc.searchFromKey(asKey("key"));

        if(member == null){
            throw new IllegalArgumentException("存在しないキーが指定されています。");
        }
        
        if(member.getPassword().equals(asString("oldPassword"))){
            member.setPassword(asString("newPassword"));
            memberSvc.update(member);
            return forward("submit.jsp");
        } else {
            errors.put("inputError", "パスワードが間違っています。");
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
        v.add("oldPassword", v.required());
        v.add("newPassword", v.required());
        v.add("newPassword2", v.required());
        return v.validate();
    }
    
    /**
     * 画面入力チェックを行ないます。
     * 
     * @return チェック結果
     */
    protected boolean customValidate() {
        
        if(asString("newPassword").equals(asString("newPassword2"))){
            return true;
        } else {
            errors.put("inputError", "新しいパスワードが一致していません。");
            return false;
        }
    }
}
