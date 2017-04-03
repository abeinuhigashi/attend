package slim3.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import slim3.taglib.AttendanceForEach.FutureIdx;

//import slim3.taglib.AttendanceForEach.FutureIdx;

/**
 * CheckPastDateTagで指定された日付が未来日の場合に、 本タグのボディ部を出力します。<br/>
 * 属性useCountをtrueに設定した場合、ページ中に本タグが出現した時点での本タグの呼出回数を保持します。
 * useCountのデフォルト値はfalseです。 呼び出し回数を取得する場合はrequestScopeからuseCount.countで取得してください。
 *
 * @author naoyuki
 *
 */
public class FutureTag implements Tag {

    @SuppressWarnings("all")
    private static final long serialVersionUID = 1L;

    private PageContext pageContext;
    private Tag parent;

    public FutureTag() {

    }

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public void setParent(Tag parent) {
        this.parent = parent;

    }

    public Tag getParent() {
        return this.parent;
    }

    public int doStartTag() throws JspException {

        CheckPastDateTag checkParent = (CheckPastDateTag) getParent();

        if (checkParent.isPast()) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }

    public int doEndTag() throws JspException {

        try {
            CheckPastDateTag checkParent = (CheckPastDateTag) getParent();
            AttendanceForEach parentsPearent =
                (AttendanceForEach) checkParent.getParent();

            if (!checkParent.isPast()) {
                FutureIdx fci =
                    (FutureIdx) parentsPearent.getPageContext().getRequest().getAttribute(
                        AttendanceForEach.FUTURE_IDX);
                fci.setIdx(fci.getIdx() + 1);
            }
        } catch (ClassCastException e) {
            throw new JspException("タグの使い方が間違ってます。");
        }
        return EVAL_PAGE;
    }

    public void release() {
    }

}
