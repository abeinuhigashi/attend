package slim3.controller.attend.manage.member.update;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.DateUtil;

import slim3.meta.MemberMeta;
import slim3.model.Member;
import slim3.service.MemberService;

public class IndexController extends Controller {

    private MemberService svc = new MemberService();
    private MemberMeta meta = new MemberMeta();

    @Override
    public Navigation run() throws Exception {

        // 入力チェック
        if (!validate()) {
            errors.put("selectError", "誰か選んでください。");
            return forward("/attend/manage/member/search/"); // index.jspだと/updateMember/に飛んでしまう
        }

        // ボタンによって遷移先を振り分ける（updateの場合は本コントローラにて処理。）
        if (asString("update") != null) {
            // 更新ボタンが押された場合
            String inputId = asString("id");

            if (inputId == null) {
                throw new IllegalArgumentException("キーとなるidが取得できません。");
            }

            Member member = svc.searchFromId(asString("id"));

            if (member == null) {
                throw new IllegalArgumentException("idに対応する団員が存在しません。id:"
                    + inputId);
            } else {
                requestScope("key", member.getKey());
                requestScope("lastName", member.getLastName());
                requestScope("firstName", member.getFirstName());
                requestScope("nickName", member.getNickName());
                requestScope("birthDay", DateUtil.toString(
                    member.getBirthDay(),
                    DateUtil.ISO_DATE_PATTERN));
                requestScope("mailAddress", member.getMailAddress());
                requestScope("part", member.getPart());
                requestScope("suspended", member.getSuspended());
                requestScope("notUsedLongTime", member.isNotUsedLongTime());
            }

            return forward("index.jsp");

        } else if (asString("delete") != null) {
            // 削除ボタンが押された場合
            return forward("/attend/manage/member/delete/");
        } else if (asString("updateAuth") != null) {
            // 権限更新ボタンが押された場合
            return forward("/attend/manage/member/updateAuth/");
        }
        
        // TODO この場合は予期していないためエラーを発生する必要がある。
        return forward("index.jsp");

    }

    /**
     * 画面入力チェックを行ないます。
     * 
     * @return チェック結果
     */
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add(meta.id, v.required());
        return v.validate();
    }
}
